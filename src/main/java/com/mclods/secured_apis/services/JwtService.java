package com.mclods.secured_apis.services;

import com.mclods.secured_apis.exceptions.AppException;

import java.util.Set;

public interface JwtService {
    String generateToken(String subject, Set<String> authorities);

    String extractUsername(String token) throws AppException;

    boolean isTokenExpired(String token) throws AppException;

    Set<String> getAuthorities(String token) throws AppException;
}
