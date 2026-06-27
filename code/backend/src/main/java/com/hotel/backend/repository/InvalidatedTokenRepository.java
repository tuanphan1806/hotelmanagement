package com.hotel.backend.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.backend.entity.InvalidatedToken;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    boolean existsByToken(String token);
    void deleteAllByExpiryTimeBefore(Date date);
}
