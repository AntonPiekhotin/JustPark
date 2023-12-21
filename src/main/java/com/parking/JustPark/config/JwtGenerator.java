package com.parking.JustPark.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * JWTGenerator class is responsible for handling JSON Web Token (JWT) generation,
 * parsing, and validation for user authentication in the application.
 */

@Component
public class JwtGenerator {

    private static final long jwtExpirationTimeMillis = 50000L;

    private static final String jwtSecret
            = "vcozinvp9iqgnva;OWBHN2]0]n]2i04tgOVWDrhgjsdlfzknvq[3-9e[8y5n0`t54gq0u39reisdV/arhA?///%^&*0q395i()O*$TIGVGRST{#T(";

    /**
     * Generates a JWT token based on the provided authentication information.
     *
     * @param authentication The authentication object containing user details.
     * @return A JWT token as a String.
     */
    public String generateToken(Authentication authentication) {
        String email = authentication.getName();

        Instant currentDate = Instant.now();
        Instant tokenExpirationInstant = currentDate.plus(jwtExpirationTimeMillis, ChronoUnit.MILLIS);
        Date tokenExpirationDate = Date.from(tokenExpirationInstant);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(tokenExpirationDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    /**
     * Extracts the email address from a given JWT token.
     *
     * @param jwtToken The JWT token to parse.
     * @return The email address extracted from the JWT token.
     */
    public String getEmailFromJWT(String jwtToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwtToken)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Validates the provided JWT token.
     *
     * @param jwtToken The JWT token to validate.
     * @return true if the token is valid; otherwise, throws an AuthenticationCredentialsNotFoundException.
     */
    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken);
            return true;
        } catch (Exception exception) {
            throw new AuthenticationCredentialsNotFoundException("JWT incorrect or expired.");
        }
    }
}
