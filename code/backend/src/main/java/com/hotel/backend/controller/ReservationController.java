package com.hotel.backend.controller;

import com.hotel.backend.dto.request.AssignRoomRequest;
import com.hotel.backend.dto.request.CancelReservationRequest;
import com.hotel.backend.dto.request.CreateReservationRequest;
import com.hotel.backend.dto.request.UpdateReservationRequest;
import com.hotel.backend.dto.response.ApiResponse;
import com.hotel.backend.dto.response.AvailabilityResponse;
import com.hotel.backend.dto.response.FinalPaymentResponse;
import com.hotel.backend.dto.response.ReservationResponse;
import com.hotel.backend.dto.response.ReservationRoomResponse;
import com.hotel.backend.entity.User;
import com.hotel.backend.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // ── Public: kiểm tra phòng trống ─────────────────────────────────────────
    @GetMapping("/availability")
public ApiResponse<List<AvailabilityResponse>> checkAvailability(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkIn,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkOut) {
    return ApiResponse.success(reservationService.checkAvailability(checkIn, checkOut));
}

    // ── Customer: tạo đặt phòng ───────────────────────────────────────────────
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReservationResponse> createReservation(
            @AuthenticationPrincipal com.hotel.backend.entity.User currentUser,
            @Valid @RequestBody CreateReservationRequest request) {
        return ApiResponse.success("Đặt phòng thành công",
                reservationService.createReservation(currentUser.getId(), request));
    }
    // ── Staff: khách vãng lai đến trực tiếp, tạo + confirm luôn ─────────────────
    @PostMapping("/walk-in")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReservationResponse> createWalkInReservation(
            @Valid @RequestBody CreateReservationRequest request,
            @RequestParam Long customerId) {
        return ApiResponse.success("Tạo đặt phòng vãng lai thành công",
                reservationService.createWalkInReservation(customerId, request));
    }

    // ── Customer: xem đặt phòng của mình ─────────────────────────────────────
    @GetMapping("/my")
    // @PreAuthorize("hasAuthority('reservation:read')")
    public ApiResponse<List<ReservationResponse>> getMyReservations(
            @AuthenticationPrincipal com.hotel.backend.entity.User currentUser) {
        return ApiResponse.success(
                reservationService.getReservationsByCustomer(currentUser.getId()));
    }

    // ── Customer/Staff: xem chi tiết đặt phòng ───────────────────────────────
    @GetMapping("/{id}")
    // @PreAuthorize("hasAuthority('reservation:read')")
    public ApiResponse<ReservationResponse> getReservation(@PathVariable Long id) {
        return ApiResponse.success(reservationService.getReservation(id));
    }

    // ── Customer/Staff: hủy đặt phòng ────────────────────────────────────────
    @PatchMapping("/cancel/{id}")
    // @PreAuthorize("hasAuthority('reservation:cancel')")
    public ApiResponse<ReservationResponse> cancelReservation(
            @PathVariable Long id,
            @RequestBody(required = false) CancelReservationRequest request) {
        if (request == null) request = new CancelReservationRequest();
        return ApiResponse.success("Hủy đặt phòng thành công",
                reservationService.cancelReservation(id, request));
    }

    // ── Staff: xác nhận đặt phòng ────────────────────────────────────────────
    @PatchMapping("/confirm/{id}")
    // @PreAuthorize("hasAuthority('reservation:confirm')")
    public ApiResponse<ReservationResponse> confirmReservation(@PathVariable Long id,@AuthenticationPrincipal com.hotel.backend.entity.User currentUser) {
        boolean isStaffOrAdmin = List.of("ADMIN", "STAFF").contains(currentUser.getType().name());
        if (!isStaffOrAdmin) {
            return ApiResponse.error(HttpStatus.METHOD_NOT_ALLOWED, "Chỉ Staff/Admin mới được xác nhận đặt phòng");
        }
        return ApiResponse.success("Xác nhận đặt phòng thành công",
                reservationService.confirmReservation(id));
    }

    // ── Staff: gán phòng cụ thể ───────────────────────────────────────────────
    @PatchMapping("/assign-room/{id}")
    // @PreAuthorize("hasAuthority('reservation:assign_room')")
    public ApiResponse<ReservationRoomResponse> assignRoom(
            @PathVariable Long id,
            @Valid @RequestBody AssignRoomRequest request) {
        return ApiResponse.success("Gán phòng thành công",
                reservationService.assignRoom(id, request));
    }


    @PatchMapping("/check-in/{id}")
    public ApiResponse<ReservationResponse> checkIn(
            @PathVariable Long id,
            @Valid @RequestBody List<AssignRoomRequest> requests) {
        return ApiResponse.success("Check-in thành công",
                reservationService.checkIn(id, requests));
    }

    @PatchMapping("/check-out/{id}")
    public ApiResponse<ReservationResponse> checkOut(@PathVariable Long id) {
        return ApiResponse.success("Check-out thành công",
                reservationService.checkOut(id));
    }

    @GetMapping("/{id}/final-payment")
    public ApiResponse<FinalPaymentResponse> calculateFinalPayment(@PathVariable Long id) {
        return ApiResponse.success(reservationService.calculateFinalPayment(id));
    }

    @GetMapping
    public ApiResponse<List<ReservationResponse>> getAllReservations() {
        return ApiResponse.success(reservationService.getAllReservations());
    }

    @PatchMapping("/{id}")
    public ApiResponse<ReservationResponse> updateReservation(
            @PathVariable Long id,
            @Valid @RequestBody UpdateReservationRequest request) {
        return ApiResponse.success("Cập nhật thành công",
                reservationService.updateReservation(id, request));
    }
}