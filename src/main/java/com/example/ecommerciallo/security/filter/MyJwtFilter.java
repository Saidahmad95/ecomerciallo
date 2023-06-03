package com.example.ecommerciallo.security.filter;

import com.example.ecommerciallo.security.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.ecommerciallo.constants.ConstantStrings.HEADER_STARTS_WITH;
import static com.example.ecommerciallo.constants.ConstantStrings.REQUEST_HEADER;
import static com.example.ecommerciallo.constants.Messages.FAILED_SET_AUTH;

@Service
@RequiredArgsConstructor
public class MyJwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = null;
            String header = request.getHeader(REQUEST_HEADER);
            if (StringUtils.hasText(header) && header.startsWith(HEADER_STARTS_WITH)) {
                jwt = header.substring(HEADER_STARTS_WITH.length());
            }

            if (jwt != null && jwtUtils.validateToken(jwt)) {
                String usernameFromToken = jwtUtils.getUsernameFromToken(jwt);
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(usernameFromToken);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        null,
                        userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error(FAILED_SET_AUTH, e);
        }
        filterChain.doFilter(request, response);

    }
}
