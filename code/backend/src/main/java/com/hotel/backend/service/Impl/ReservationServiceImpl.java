package com.hotel.backend.service.Impl;

import com.hotel.backend.constant.AssignStatus;
import com.hotel.backend.constant.CleaningStatus;
import com.hotel.backend.constant.HoldStatus;
import com.hotel.backend.constant.ReservationStatus;
import com.hotel.backend.constant.RoomStatus;
import com.hotel.backend.dto.request.AssignRoomRequest;
import com.hotel.backend.dto.request.CancelReservationRequest;
import com.hotel.backend.dto.request.CreateReservationRequest;
import com.hotel.backend.dto.request.RoomTypeItemRequest;
import com.hotel.backend.dto.request.UpdateReservationRequest;
import com.hotel.backend.dto.request.GuestRequest;
import com.hotel.backend.dto.response.*;
import com.hotel.backend.entity.*;
import com.hotel.backend.exception.AppException;
import com.hotel.backend.exception.ErrorCode;
import com.hotel.backend.repository.*;
import com.hotel.backend.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j(topic = "RESERVATION_SERVICE")
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private static final int HOLD_MINUTES = 15;

    private final ReservationRepository          reservationRepository;
    private final ReservationRoomTypeRepository  reservationRoomTypeRepository;
    private final ReservationRoomRepository      reservationRoomRepository;
    private final RoomHoldRepository             roomHoldRepository;
    private final RoomTypeRepository             roomTypeRepository;
    private final UserRepository                 userRepository;
    private final RoomRepository                 roomRepository;
    private final GuestRepository                guestRepository;
    // ─────────────────────────────────────────────────────────────────────────
    // Tạo đặt phòng
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReservationResponse createReservation(Long customerId, CreateReservationRequest request) {
        log.info("createReservation: customerId={} checkIn={} checkOut={}",
                customerId, request.getCheckIn(), request.getCheckOut());

        // 1. Validate ngày
        validateDates(request.getCheckIn(), request.getCheckOut());

        // 2. Lấy customer
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        long hours = ChronoUnit.HOURS.between(request.getCheckIn(), request.getCheckOut());

        // 3. Check availability + tính tiền cho từng room type
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<RoomTypeWithPrice> roomTypeWithPrices = new ArrayList<>();

        for (RoomTypeItemRequest item : request.getRoomTypes()) {
            RoomType roomType = roomTypeRepository.findById(item.getRoomTypeId())
                    .orElseThrow(() -> new AppException(ErrorCode.ROOM_TYPE_NOT_FOUND));

            checkAvailabilityOrThrow(roomType, item.getQuantity(),
                    request.getCheckIn(), request.getCheckOut(), null);

            BigDecimal subtotal = roomType.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()))
                    .multiply(BigDecimal.valueOf(hours));

            totalAmount = totalAmount.add(subtotal);
            roomTypeWithPrices.add(new RoomTypeWithPrice(roomType, item.getQuantity(),
                    roomType.getPrice(), subtotal));
        }

        // 4. Tạo Reservation
        Reservation reservation = Reservation.builder()
                .reservationCode(generateCode())
                .customer(customer)
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .totalAmount(totalAmount)
                .guestCount(request.getGuestCount())
                .note(request.getNote())
                .status(ReservationStatus.DRAFT)
                .build();
        reservationRepository.save(reservation);

        // 5. Tạo ReservationRoomType + RoomHold + ReservationRoom (placeholder)
        List<ReservationRoomTypeResponse> roomTypeResponses = new ArrayList<>();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(HOLD_MINUTES);

        for (RoomTypeWithPrice item : roomTypeWithPrices) {
            ReservationRoomType rrt = ReservationRoomType.builder()
                    .reservation(reservation)
                    .roomType(item.roomType())
                    .quantity(item.quantity())
                    .roomPrice(item.price())
                    .subtotal(item.subtotal())
                    .build();
            reservationRoomTypeRepository.save(rrt);

            // RoomHold
            RoomHold hold = RoomHold.builder()
                    .reservationRoomType(rrt)
                    .expiresAt(expiresAt)
                    .status(HoldStatus.ACTIVE)
                    .build();
            roomHoldRepository.save(hold);
            //dat coc de khong bi huy
            // ReservationRoom placeholder (1 row per unit, room chưa assign)
            for (int i = 0; i < item.quantity(); i++) {
                ReservationRoom rr = ReservationRoom.builder()
                        .reservationRoomType(rrt)
                        .status(AssignStatus.PENDING_ASSIGN)
                        .build();
                reservationRoomRepository.save(rr);
            }

            ReservationRoomTypeResponse rrtRes = ReservationRoomTypeResponse.from(rrt);
            rrtRes.setRoomHold(RoomHoldResponse.from(hold));
            roomTypeResponses.add(rrtRes);
        }

        log.info("Reservation created: code={} total={}", reservation.getReservationCode(), totalAmount);
        return ReservationResponse.fromWithDetails(reservation, roomTypeResponses);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Lấy đặt phòng
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public ReservationResponse getReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findByIdWithDetails(reservationId)
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND));

        List<ReservationRoomTypeResponse> roomTypeResponses = reservation.getRoomTypes().stream()
                .map(rrt -> {
                    ReservationRoomTypeResponse res = ReservationRoomTypeResponse.from(rrt);
                    if (rrt.getRoomHold() != null) {
                        res.setRoomHold(RoomHoldResponse.from(rrt.getRoomHold()));
                    }
                    return res;
                }).toList();

        return ReservationResponse.fromWithDetails(reservation, roomTypeResponses);
    }

    @Override
@Transactional(readOnly = true)
public List<ReservationResponse> getReservationsByCustomer(Long customerId) {
    return reservationRepository.findByCustomerIdOrderByCreatedAtDesc(customerId)
            .stream()
            .map(reservation -> {
                List<ReservationRoomTypeResponse> roomTypeResponses = reservation.getRoomTypes().stream()
                        .map(rrt -> {
                            ReservationRoomTypeResponse res = ReservationRoomTypeResponse.from(rrt);
                            if (rrt.getRoomHold() != null) {
                                res.setRoomHold(RoomHoldResponse.from(rrt.getRoomHold()));
                            }
                            return res;
                        }).toList();
                return ReservationResponse.fromWithDetails(reservation, roomTypeResponses);
            })
            .toList();
}
    // ─────────────────────────────────────────────────────────────────────────
    // Hủy đặt phòng
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReservationResponse cancelReservation(Long reservationId, CancelReservationRequest request) {
        Reservation reservation = reservationRepository.findByIdWithDetails(reservationId)
        .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND));

        if (!List.of(ReservationStatus.DRAFT, ReservationStatus.CONFIRMED)
                .contains(reservation.getStatus())) {
            throw new AppException(ErrorCode.RESERVATION_CANNOT_CANCEL);
        }

        // Release tất cả hold
        reservation.getRoomTypes().forEach(rrt -> {
            if (rrt.getRoomHold() != null
                    && rrt.getRoomHold().getStatus() == HoldStatus.ACTIVE) {
                rrt.getRoomHold().setStatus(HoldStatus.RELEASED);
                roomHoldRepository.save(rrt.getRoomHold());
            }
        });

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.setCancellationReason(request.getCancellationReason());
        reservationRepository.save(reservation);

        log.info("Reservation cancelled: id={}", reservationId);
        return ReservationResponse.from(reservation);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Confirm đặt phòng
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReservationResponse confirmReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findByIdWithDetails(reservationId)
        .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND));

        if (reservation.getStatus() != ReservationStatus.DRAFT) {
            throw new AppException(ErrorCode.RESERVATION_CANNOT_CONFIRM);
        }

        // Kiểm tra hold chưa expired
        reservation.getRoomTypes().forEach(rrt -> {
            RoomHold hold = rrt.getRoomHold();
            if (hold == null || hold.getStatus() != HoldStatus.ACTIVE) {
                throw new AppException(ErrorCode.ROOM_HOLD_EXPIRED);
            }
            if (hold.getExpiresAt().isBefore(LocalDateTime.now())) {
                throw new AppException(ErrorCode.ROOM_HOLD_EXPIRED);
            }
            // Convert hold sang CONVERTED
            hold.setStatus(HoldStatus.CONVERTED);
            roomHoldRepository.save(hold);
        });

        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservationRepository.save(reservation);

        log.info("Reservation confirmed: id={}", reservationId);
        return ReservationResponse.from(reservation);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Check availability
    // ─────────────────────────────────────────────────────────────────────────
    @Override
@Transactional(readOnly = true)
public List<AvailabilityResponse> checkAvailability(LocalDateTime checkIn, LocalDateTime checkOut) {
    validateDates(checkIn, checkOut);
    LocalDateTime now = LocalDateTime.now();
    long hours = ChronoUnit.HOURS.between(checkIn, checkOut);
 
    return roomTypeRepository.findAll().stream().map(rt -> {
        int total     = roomTypeRepository.countAvailableRoomsByType(rt.getId());
        int booked    = reservationRoomTypeRepository.countBookedQuantity(rt.getId(), checkIn, checkOut);
        int held      = roomHoldRepository.countActiveHeldQuantity(rt.getId(), checkIn, checkOut, now);
        int available = Math.max(0, total - booked - held);
 
        return AvailabilityResponse.builder()
                .roomTypeId(rt.getId())
                .roomTypeName(rt.getTypeName())
                .description(rt.getDescription())
                .pricePerHour(rt.getPrice())
                .imageUrl(rt.getImageUrl())
                .checkIn(checkIn)
                .checkOut(checkOut)
                .totalHours(hours)
                .totalRooms(total)
                .bookedRooms(booked)
                .heldRooms(held)
                .availableRooms(available)
                .build();
    }).toList();
}

    // ─────────────────────────────────────────────────────────────────────────
    // Gán phòng cụ thể (staff)
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReservationRoomResponse assignRoom(Long reservationId, AssignRoomRequest request) {
        ReservationRoom reservationRoom = reservationRoomRepository.findById(request.getReservationRoomId())
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_ROOM_NOT_FOUND));

        // Kiểm tra thuộc đúng reservation
        if (!reservationRoom.getReservationRoomType().getReservation().getId().equals(reservationId)) {
            throw new AppException(ErrorCode.RESERVATION_ROOM_NOT_FOUND);
        }

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        // Kiểm tra đúng room type
        Long expectedRoomTypeId = reservationRoom.getReservationRoomType().getRoomType().getId();
        if (!room.getRoomType().getId().equals(expectedRoomTypeId)) {
            throw new AppException(ErrorCode.ROOM_WRONG_TYPE);
        }

        // Kiểm tra chưa assign phòng này cho reservation
        if (reservationRoomRepository.existsByRoomIdAndReservationRoomTypeReservationId(
                room.getId(), reservationId)) {
            throw new AppException(ErrorCode.ROOM_ALREADY_ASSIGNED);
        }

        reservationRoom.setRoom(room);
        reservationRoom.setStatus(AssignStatus.ASSIGNED);
        reservationRoomRepository.save(reservationRoom);

        log.info("Room assigned: reservationRoomId={} roomId={}", request.getReservationRoomId(), room.getId());
        return ReservationRoomResponse.from(reservationRoom);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReservationResponse checkIn(Long reservationId, List<AssignRoomRequest> requests) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND));

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new AppException(ErrorCode.RESERVATION_CANNOT_CHECKIN);
        }

        // Lấy tất cả ReservationRoom cần assign
        List<ReservationRoom> reservationRooms = 
                reservationRoomRepository.findAllByReservationId(reservationId);

        if (requests.size() != reservationRooms.size()) {
            throw new AppException(ErrorCode.INVALID_REQUEST,
                    "Số phòng gán không khớp với số phòng đặt");
        }

        // Gán phòng + CHECKED_IN từng ReservationRoom
        for (AssignRoomRequest req : requests) {
            ReservationRoom rr = reservationRooms.stream()
                    .filter(r -> r.getId().equals(req.getReservationRoomId()))
                    .findFirst()
                    .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_ROOM_NOT_FOUND));
            
            Room room = roomRepository.findById(req.getRoomId())
                    .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

            // Kiểm tra đúng room type
            Long expectedRoomTypeId = rr.getReservationRoomType().getRoomType().getId();
            if (!room.getRoomType().getId().equals(expectedRoomTypeId)) {
                throw new AppException(ErrorCode.ROOM_WRONG_TYPE);
            }

            // Kiểm tra phòng chưa bị assign cho reservation khác đang CHECKED_IN
            boolean isOccupied = reservationRoomRepository.existsByRoomIdAndStatus(room.getId(), AssignStatus.CHECKED_IN);
            if (isOccupied) {
                throw new AppException(ErrorCode.ROOM_NOT_AVAILABLE,
                        String.format("Phòng '%s' đang có khách", room.getRoomName()));
            }

            long primaryCount = req.getGuests().stream()
            .filter(g -> Boolean.TRUE.equals(g.getIsPrimary()))
            .count();
            if (primaryCount == 0) {
                throw new AppException(ErrorCode.GUEST_PRIMARY_REQUIRED);
            }
            if (primaryCount > 1) {
                throw new AppException(ErrorCode.GUEST_MULTIPLE_PRIMARY);
            }

            //nv check lai de xem co the tao nhieu guest
            List<Guest> existingGuests = guestRepository.findByReservationRoomId(rr.getId());
            if (existingGuests.isEmpty()) {
                for (GuestRequest g : req.getGuests()) {
                    Guest guest = Guest.builder()
                            .reservationRoom(rr)
                            .fullName(g.getFullName())
                            .phone(g.getPhone())
                            .email(g.getEmail())
                            .idCardNumber(g.getIdCardNumber())
                            .idCardType(g.getIdCardType())
                            .dateOfBirth(g.getDateOfBirth())
                            .nationality(g.getNationality())
                            .isPrimary(g.getIsPrimary())
                            .build();
                    guestRepository.save(guest);
                }
            } else {
                log.info("ReservationRoom {} đã có guest, bỏ qua tạo mới", rr.getId());
            }
            rr.setRoom(room);
            rr.setStatus(AssignStatus.CHECKED_IN);
            reservationRoomRepository.save(rr);

            room.setStatus(RoomStatus.CHECKED_IN);
            roomRepository.save(room);
        }

        reservation.setStatus(ReservationStatus.CHECKED_IN);
        reservationRepository.save(reservation);

        log.info("Reservation checked-in: id={}", reservationId);
        return ReservationResponse.from(reservation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReservationResponse checkOut(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND));

        if (reservation.getStatus() != ReservationStatus.CHECKED_IN) {
            throw new AppException(ErrorCode.RESERVATION_CANNOT_CHECKOUT);
        }

        // Giải phóng phòng → CHECKED_OUT
        List<ReservationRoom> rooms = 
                reservationRoomRepository.findAllByReservationId(reservationId);

        LocalDateTime now = LocalDateTime.now();

        rooms.forEach(rr -> {
            rr.setStatus(AssignStatus.CHECKED_OUT);
            reservationRoomRepository.save(rr);
            Room room = rr.getRoom();
            if (room != null) {
                room.setStatus(RoomStatus.AVAILABLE);
                room.setCleaningStatus(CleaningStatus.CLEAN); // cần dọn trước khi cho khách tiếp theo
                roomRepository.save(room);
            }

            // Bỏ gán guest khỏi room — giữ lại data, đánh dấu thời điểm checkout
            List<Guest> guests = guestRepository.findByReservationRoomId(rr.getId());
            guests.forEach(guest -> {
                guest.setReservationRoom(null);
                guest.setCheckedOutAt(now);
                guestRepository.save(guest);
            });

        });

        reservation.setStatus(ReservationStatus.CHECKED_OUT);
        reservationRepository.save(reservation);

        log.info("Reservation checked-out: id={}", reservationId);
        return ReservationResponse.from(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAllWithDetails()
                .stream()
                .map(reservation -> {
                    List<ReservationRoomTypeResponse> roomTypeResponses = reservation.getRoomTypes().stream()
                            .map(rrt -> {
                                ReservationRoomTypeResponse res = ReservationRoomTypeResponse.from(rrt);
                                if (rrt.getRoomHold() != null) {
                                    res.setRoomHold(RoomHoldResponse.from(rrt.getRoomHold()));
                                }
                                return res;
                            }).toList();
                    return ReservationResponse.fromWithDetails(reservation, roomTypeResponses);
                })
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReservationResponse updateReservation(Long reservationId, UpdateReservationRequest request) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND));

        if (reservation.getStatus() != ReservationStatus.DRAFT) {
            throw new AppException(ErrorCode.RESERVATION_CANNOT_UPDATE);
        }

        if (request.getGuestCount() != null) {
            reservation.setGuestCount(request.getGuestCount());
        }
        if (request.getNote() != null) {
            reservation.setNote(request.getNote());
        }

        reservationRepository.save(reservation);
        log.info("Reservation updated: id={}", reservationId);
        return ReservationResponse.from(reservation);
    }



    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────
    private void validateDates(LocalDateTime checkIn, LocalDateTime checkOut) {
    if (!checkOut.isAfter(checkIn)) {
        throw new AppException(ErrorCode.RESERVATION_INVALID_DATE);
    }
    if (checkIn.isBefore(LocalDateTime.now())) {
        throw new AppException(ErrorCode.RESERVATION_CHECKIN_PAST);
    }
}

    private void checkAvailabilityOrThrow(RoomType roomType, int requested,
                                          LocalDateTime checkIn, LocalDateTime checkOut,
                                          Long excludeReservationId) {
        int total  = roomTypeRepository.countAvailableRoomsByType(roomType.getId());
        int booked = excludeReservationId == null
                ? reservationRoomTypeRepository.countBookedQuantity(roomType.getId(), checkIn, checkOut)
                : reservationRoomTypeRepository.countBookedQuantityExcluding(roomType.getId(), excludeReservationId, checkIn, checkOut);
        int held   = excludeReservationId == null
                ? roomHoldRepository.countActiveHeldQuantity(roomType.getId(), checkIn, checkOut, LocalDateTime.now())
                : roomHoldRepository.countActiveHeldQuantityExcluding(roomType.getId(), excludeReservationId, checkIn, checkOut, LocalDateTime.now());

        int available = total - booked - held;
        if (available < requested) {
            throw new AppException(ErrorCode.ROOM_NOT_AVAILABLE,
                    String.format("Loại phòng '%s' chỉ còn %d phòng trống", roomType.getTypeName(), available));
        }
    }

    private String generateCode() {
        String code;
        do {
            code = "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (reservationRepository.existsByReservationCode(code));
        return code;
    }

    // Inner record tạm để truyền data giữa các bước
    private record RoomTypeWithPrice(RoomType roomType, int quantity,
                                     BigDecimal price, BigDecimal subtotal) {}
}