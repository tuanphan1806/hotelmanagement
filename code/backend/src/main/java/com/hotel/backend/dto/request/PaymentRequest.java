package com.hotel.backend.dto.request;
import lombok.Data;
import java.math.BigDecimal;
import com.hotel.backend.constant.PaymentMethod;


@Data
public class PaymentRequest {
    private Long reservationId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private String transactionCode;
}