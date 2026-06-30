package com.hotel.backend.scheduled;
 
import com.hotel.backend.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
 
import java.time.LocalDateTime;
 
@Slf4j(topic = "GUEST_CLEANUP_SCHEDULER")
@Component
@RequiredArgsConstructor
public class GuestCleanupScheduler {
 
    private final GuestRepository guestRepository;
 
    private static final int RETENTION_DAYS = 7;
 
    // Chạy mỗi ngày lúc 2h sáng
    @Scheduled(cron = "0 0 2 * * *")
    @Transactional(rollbackFor = Exception.class)
    public void cleanupExpiredGuests() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(RETENTION_DAYS);
        int deleted = guestRepository.bulkDeleteExpiredGuests(cutoff);
        if (deleted > 0) {
            log.info("Deleted {} expired guest records (checked out before {})", deleted, cutoff);
        }
    }
}