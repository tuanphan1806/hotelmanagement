package com.hotel.backend.dto.request;
 
import lombok.*;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancelReservationRequest {
 
    private String cancellationReason;
}