package com.hotel.backend.dto.request;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import com.hotel.backend.constant.PaymentProvider;


@Data
public class PaymentRequest {
 
    @NotNull(message = "bookingId không được để trống")
    private Long bookingId;
 
    @NotNull(message = "amount không được để trống")
    @Min(value = 10000, message = "Số tiền tối thiểu là 10,000 VND")
    private Long amount;
 
    private PaymentProvider provider;
 
    private String orderInfo; // Nội dung thanh toán (tuỳ chọn)
}
