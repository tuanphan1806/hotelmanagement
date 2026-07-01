package com.hotel.backend.dto.request;
import lombok.Data;
import java.math.BigDecimal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.hotel.backend.constant.PaymentMethod;
import com.hotel.backend.constant.PaymentProvider;


@Data
public class PaymentRequest {
 
    @NotBlank(message = "bookingId không được để trống")
    private Long bookingId;
 
    @NotNull(message = "amount không được để trống")
    @Min(value = 10000, message = "Số tiền tối thiểu là 10,000 VND")
    private Long amount;
 
    @NotNull(message = "provider không được để trống")
    private PaymentProvider provider;
 
    private String orderInfo; // Nội dung thanh toán (tuỳ chọn)
}