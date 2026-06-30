package com.hotel.backend.controller;
 
import com.hotel.backend.dto.response.ApiResponse;
import com.hotel.backend.dto.response.GuestResponse;
import com.hotel.backend.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {
 
    private final GuestService guestService;
 
    // ── Staff: xem danh sách khách trong 1 phòng ─────────────────────────────
    @GetMapping("/reservation-room/{reservationRoomId}")
    public ApiResponse<List<GuestResponse>> getGuestsByReservationRoom(
            @PathVariable Long reservationRoomId) {
        return ApiResponse.success(guestService.getGuestsByReservationRoom(reservationRoomId));
    }
 
    // ── Staff: xem toàn bộ khách của 1 reservation ───────────────────────────
    @GetMapping("/reservation/{reservationId}")
    public ApiResponse<List<GuestResponse>> getGuestsByReservation(
            @PathVariable Long reservationId) {
        return ApiResponse.success(guestService.getGuestsByReservation(reservationId));
    }
}