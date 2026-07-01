package com.hotel.backend.dto.response;

import com.hotel.backend.constant.PaymentProvider;
import com.hotel.backend.constant.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private String transactionId;   // ID giao dịch nội bộ
    private Long bookingId;
    private PaymentProvider provider;
    private PaymentStatus status;
    private Long amount;
    private String paymentUrl;      // URL redirect sang cổng thanh toán
    private String message;
    private LocalDateTime createdAt;
}
