package com.hotel.backend.scheduled;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hotel.backend.repository.InvalidatedTokenRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "TOKEN-CLEANUP-JOB")
public class TokenCleanupJob {

    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Scheduled(cron = "0 0 * * * *") // mỗi 1 tiếng
    // @Scheduled(fixedRate = 10000) // 10 giâ
    @Transactional
    public void cleanExpiredTokens() {
        log.info("Scheduler is running...");
        invalidatedTokenRepository.deleteAllByExpiryTimeBefore(new Date());
        log.info("Cleaned expired invalidated tokens");
    }
}
