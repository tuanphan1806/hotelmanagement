package com.hotel.backend.scheduled;

import com.hotel.backend.constant.ReservationStatus;
import com.hotel.backend.entity.RoomHold;
import com.hotel.backend.repository.ReservationRepository;
import com.hotel.backend.repository.RoomHoldRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j(topic = "ROOM_HOLD_SCHEDULER")
@Component
@RequiredArgsConstructor
public class RoomHoldExpiryScheduler {

    private final RoomHoldRepository      roomHoldRepository;
    private final ReservationRepository   reservationRepository;

    /**
     * Chạy mỗi 1 phút, expire hold đã hết hạn
     * và tự động CANCEL reservation DRAFT tương ứng
     */
    @Scheduled(fixedDelay = 60_000)
    @Transactional(rollbackFor = Exception.class)
    public void expireHolds() {
        LocalDateTime now = LocalDateTime.now();

        // 1. Lấy danh sách hold hết hạn
        List<RoomHold> expiredHolds = roomHoldRepository.findExpiredActiveHolds(now);
        if (expiredHolds.isEmpty()) return;

        log.info("Found {} expired holds", expiredHolds.size());

        for (RoomHold hold : expiredHolds) {
            hold.setStatus(com.hotel.backend.constant.HoldStatus.EXPIRED);
            roomHoldRepository.save(hold);

            // 2. Cancel reservation DRAFT nếu tất cả hold của reservation đó đã expired
            var reservation = hold.getReservationRoomType().getReservation();
            if (reservation.getStatus() == ReservationStatus.DRAFT) {
                boolean allExpired = reservation.getRoomTypes().stream()
                        .allMatch(rrt -> rrt.getRoomHold() == null
                                || rrt.getRoomHold().getStatus() != com.hotel.backend.constant.HoldStatus.ACTIVE
                                || rrt.getRoomHold().getExpiresAt().isBefore(now));

                if (allExpired) {
                    reservation.setStatus(ReservationStatus.CANCELLED);
                    reservation.setCancellationReason("Hết thời gian giữ chỗ");
                    reservationRepository.save(reservation);
                    log.info("Auto-cancelled reservation: id={} code={}",
                            reservation.getId(), reservation.getReservationCode());
                }
            }
        }
    }
}