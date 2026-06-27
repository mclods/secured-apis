package com.mclods.secured_apis.services.impl;

import com.mclods.secured_apis.exceptions.AppException;
import com.mclods.secured_apis.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.expirationMS}")
    private long expirationMS;

    @Override
    public String generateToken(String subject, Set<String> authorities) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new  Date())
                .expiration(new  Date(System.currentTimeMillis() + expirationMS))
                .claims()
                .add("authorities", authorities)
                .and()
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public String extractUsername(String token) throws AppException {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenExpired(String token) throws AppException {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    @Override
    public Set<String> getAuthorities(String token) throws AppException {
        Set<?> authorities = extractClaims(token, (claims ->  claims.get("authorities", Set.class)));

        if(authorities == null) {
            return Set.of();
        } else {
            return authorities.stream().map(Object::toString).collect(Collectors.toSet());
        }
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) throws AppException {
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .json(new JacksonDeserializer<>(Map.of("authorities", Set.class)))
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (SignatureException e) {
            throw new AppException("Invalid JWT Token!");
        } catch (ExpiredJwtException e) {
            throw new AppException("Expired JWT token!");
        }

        return claims != null ? claimsResolver.apply(claims) : null;
    }
}
