package dev.awn.apigateway.common.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JwtUtils {

    @Value("${token.secret.key}")
    private String jwtSecretKey;

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        String rolesClaim = claims.get("roles", String.class);

        // TODO : Careful of return null;
        if(rolesClaim != null) {
            return Arrays.asList(rolesClaim.split(","));
        }

        return null;
    }

    public void validateToken(final String token) {
        Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
