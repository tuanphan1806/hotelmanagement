package com.hotel.backend.scheduled;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hotel.backend.repository.InvalidatedTokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupJob {

    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Scheduled(cron = "0 0 * * * *") // mỗi 1 tiếng
    public void cleanExpiredTokens() {
        invalidatedTokenRepository.deleteAllByExpiryTimeBefore(new Date());
        log.info("Cleaned expired invalidated tokens");
    }
}
