package com.hotel.backend.service.Impl;
 
import com.hotel.backend.dto.response.GuestResponse;
import com.hotel.backend.repository.GuestRepository;
import com.hotel.backend.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.List;
 
@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {
 
    private final GuestRepository guestRepository;
 
    @Override
    @Transactional(readOnly = true)
    public List<GuestResponse> getGuestsByReservationRoom(Long reservationRoomId) {
        return guestRepository.findByReservationRoomId(reservationRoomId)
                .stream()
                .map(GuestResponse::from)
                .toList();
    }
 
    @Override
    @Transactional(readOnly = true)
    public List<GuestResponse> getGuestsByReservation(Long reservationId) {
        return guestRepository.findAllByReservationId(reservationId)
                .stream()
                .map(GuestResponse::from)
                .toList();
    }
}