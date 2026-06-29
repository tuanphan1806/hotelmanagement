package com.hotel.backend.entity;

import com.hotel.backend.constant.PaymentProvider;
import com.hotel.backend.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    // Mã đặt phòng trong hệ thống khách sạn
    @Column(name = "booking_id", nullable = false)
    private String bookingId;

    // Mã giao dịch nội bộ (gửi sang cổng thanh toán)
    @Column(name = "txn_ref", unique = true, nullable = false)
    private String txnRef;

    // Mã giao dịch từ cổng thanh toán trả về
    @Column(name = "provider_txn_id")
    private String providerTxnId;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private PaymentProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    // Số tiền (VND)
    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "currency", nullable = false)
    private String currency = "VND";

    // Nội dung thanh toán
    @Column(name = "order_info")
    private String orderInfo;

    // IP khách hàng
    @Column(name = "ip_address")
    private String ipAddress;

    // Mã ngân hàng (VNPay)
    @Column(name = "bank_code")
    private String bankCode;

    // Mã phản hồi từ cổng thanh toán
    @Column(name = "response_code")
    private String responseCode;

    // Thông báo lỗi (nếu có)
    @Column(name = "message")
    private String message;

    // Token lưu thẻ (dùng cho thanh toán lần sau)
    @Column(name = "card_token")
    private String cardToken;

    // Mã giao dịch hoàn tiền
    @Column(name = "refund_txn_id")
    private String refundTxnId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Thời gian thanh toán thành công
    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}
