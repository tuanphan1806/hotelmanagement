package com.hotel.backend.service;
 
import com.hotel.backend.dto.request.AssignRoomRequest;
import com.hotel.backend.dto.request.CancelReservationRequest;
import com.hotel.backend.dto.request.CreateReservationRequest;
import com.hotel.backend.dto.request.UpdateReservationRequest;
import com.hotel.backend.dto.response.AvailabilityResponse;
import com.hotel.backend.dto.response.ReservationResponse;
import com.hotel.backend.dto.response.ReservationRoomResponse;
 

import java.time.LocalDateTime;
import java.util.List;
 
public interface ReservationService {
 
    // Khách tạo đặt phòng → tạo Reservation + ReservationRoomType + RoomHold
    ReservationResponse createReservation(Long customerId, CreateReservationRequest request);

    ReservationResponse createWalkInReservation(Long customerId, CreateReservationRequest request);

    // Được gọi từ Payment/VNPay khi thanh toán thành công
    void convertHoldsAfterPayment(Long reservationId);
    // Lấy chi tiết đặt phòng
    ReservationResponse getReservation(Long reservationId);
 
    // Lấy danh sách đặt phòng của khách
    List<ReservationResponse> getReservationsByCustomer(Long customerId);
 
    // Khách/Staff hủy đặt phòng
    ReservationResponse cancelReservation(Long reservationId, CancelReservationRequest request);
 
    // Staff xác nhận đặt phòng (sau thanh toán hoặc duyệt thủ công)
    ReservationResponse confirmReservation(Long reservationId);
 
    // Kiểm tra phòng trống theo ngày
    List<AvailabilityResponse> checkAvailability(LocalDateTime checkIn, LocalDateTime checkOut);
 
    // Staff gán phòng cụ thể cho ReservationRoom
    ReservationRoomResponse assignRoom(Long reservationId, AssignRoomRequest request);

    ReservationResponse checkIn(Long reservationId, List<AssignRoomRequest> requests);
    ReservationResponse checkOut(Long reservationId);

    List<ReservationResponse> getAllReservations();
    ReservationResponse updateReservation(Long reservationId, UpdateReservationRequest request);
}