package com.example.ecommerciallo.services.impl;

import com.example.ecommerciallo.entities.Role;
import com.example.ecommerciallo.entities.User;
import com.example.ecommerciallo.exceptions.MyLoginException;
import com.example.ecommerciallo.exceptions.SignUpException;
import com.example.ecommerciallo.payloads.request.LoginDto;
import com.example.ecommerciallo.payloads.request.SignUpDto;
import com.example.ecommerciallo.payloads.response.LoginResponse;
import com.example.ecommerciallo.payloads.response.UserResponse;
import com.example.ecommerciallo.repos.RoleRepo;
import com.example.ecommerciallo.repos.UserRepo;
import com.example.ecommerciallo.security.user.UserDetailsImpl;
import com.example.ecommerciallo.security.utils.JwtUtils;
import com.example.ecommerciallo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.LoginException;
import java.util.*;

import static com.example.ecommerciallo.constants.Messages.*;
import static com.example.ecommerciallo.enums.ERole.ROLE_ADMIN;
import static com.example.ecommerciallo.enums.ERole.ROLE_USER;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final RoleRepo roleRepo;
    private final AuthenticationManager authMngr;
    private final JwtUtils jwtUtils;


    @Override
    public UserResponse signUp(SignUpDto dto) {

        checkSignUpDto(dto);
        User builtUser = userBuilder(dto);
        User savedUser = userRepo.save(setRoleToUser(dto.getRoles(), builtUser));
        return new UserResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginDto loginDto) {
        Authentication auth = checkLoginDto(loginDto);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtUtils.generateToken(auth);

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        return LoginResponse.builder()
                .id(userDetails.getId())
                .token(jwt)
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }

    private Authentication checkLoginDto(LoginDto loginDto) {
        Map<String, String> errors = new HashMap<>();
        Authentication auth = null;
        try {
            auth = authMngr.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        } catch (DisabledException e) {
            errors.put("disabled", format(USER_IS_DISABLED, loginDto.getUsername()));
        } catch (LockedException e) {
            errors.put("locked", format(USER_IS_LOCKED, loginDto.getUsername()));
        } catch (BadCredentialsException e) {
            errors.put("credentials", format(BAD_CREDENTIALS));
        } catch (Exception ex) {
            errors.put("error", ex.getMessage());
        }
        if (!errors.isEmpty()) {
            throw new MyLoginException(errors);
        }
        return auth;
    }

    private void checkSignUpDto(SignUpDto dto) {
        Map<String, String> errors = new HashMap<>();
        if (userRepo.existsByUsername(dto.getUsername()))
            errors.put("username", format(USERNAME_IS_TAKEN, dto.getUsername()));

        if (userRepo.existsByEmail(dto.getEmail()))
            errors.put("email", format(EMAIL_IS_TAKEN, dto.getEmail()));

        if (userRepo.existsByPhone(dto.getPhone()))
            errors.put("phone", format(PHONE_IS_TAKEN, dto.getPhone()));

        if (!errors.isEmpty())
            throw new SignUpException(errors);
    }

    private User setRoleToUser(Set<String> roles, User builtUser) {
        Set<Role> newRoles = new HashSet<>();
        if (roles == null || roles.isEmpty()) {
            Role userRole = roleRepo.findByRoleName(ROLE_USER).orElseThrow(
                    () -> new ResponseStatusException(NOT_FOUND, format(ROLE_NOT_FOUND, ROLE_USER.name())));
            newRoles.add(userRole);
        } else {
            roles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = roleRepo.findByRoleName(ROLE_ADMIN).orElseThrow(
                            () -> new ResponseStatusException(NOT_FOUND, format(ROLE_NOT_FOUND, ROLE_ADMIN.name())));
                    newRoles.add(adminRole);
                } else {
                    Role userRole = roleRepo.findByRoleName(ROLE_USER).orElseThrow(
                            () -> new ResponseStatusException(NOT_FOUND, format(ROLE_NOT_FOUND, ROLE_USER.name())));
                    newRoles.add(userRole);
                }
            });
        }
        builtUser.setRoles(newRoles);
        return builtUser;
    }


    private User userBuilder(SignUpDto signUpDto) {
        return User.builder()
                .username(signUpDto.getUsername())
                .firstname(signUpDto.getFirstname())
                .lastname(signUpDto.getLastname())
                .phone(signUpDto.getPhone())
                .email(signUpDto.getEmail())
                .password(encoder.encode(signUpDto.getPassword()))
                .build();

    }
}
