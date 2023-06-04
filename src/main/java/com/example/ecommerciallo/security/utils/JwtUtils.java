package com.example.ecommerciallo.security.utils;

import com.example.ecommerciallo.security.user.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import static com.example.ecommerciallo.constants.Messages.*;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.sign-in.secret-key}")
    private String jwtSecret;

    @Value("${jwt.exp.date}")
    private long jwtExpirationMs;


    public String generateToken(Authentication auth) {
        UserDetailsImpl principal = (UserDetailsImpl) auth.getPrincipal();
        try {
            return Jwts
                    .builder()
                    .setClaims(new HashMap<>())
                    .setSubject(principal.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                    .signWith(getSignInKey(), HS256)
                    .compact();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(jwt);
            return !isTokenExpired(jwt);
        } catch (MalformedJwtException e) {
            logger.error(INVALID_JWT, e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error(JWT_EXPIRED, e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error(JWT_UNSUPPORTED, e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(JWT_EMPTY, e.getMessage());
        }
        return false;
    }

    private boolean isTokenExpired(String jwt) {
        Date expiration = Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }

    public String getUsernameFromToken(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();

    }
}
