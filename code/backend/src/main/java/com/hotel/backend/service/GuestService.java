package com.hotel.backend.service;
 
import com.hotel.backend.dto.response.GuestResponse;
 
import java.util.List;
 
public interface GuestService {
    List<GuestResponse> getGuestsByReservationRoom(Long reservationRoomId);
    List<GuestResponse> getGuestsByReservation(Long reservationId);
}