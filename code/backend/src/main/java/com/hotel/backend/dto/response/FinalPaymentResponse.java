package com.hotel.backend.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinalPaymentResponse {
    private Long reservationId;
    private Long totalAmount;
    private Long paidAmount;
    private Long remainingAmount;
    private boolean fullyPaid;
}