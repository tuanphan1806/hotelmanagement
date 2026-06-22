package com.hotel.backend.entity;

import com.hotel.backend.constant.PaymentMethod;
import com.hotel.backend.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "payments",
    indexes = {
        @Index(name = "idx_payment_status", columnList = "payment_status"),
        @Index(name = "idx_payment_transaction_code", columnList = "transaction_code")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Payment extends AbstractEntity<Long> implements Serializable {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reservation_id", unique = true, nullable = false)
    private Reservation reservation;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    @Builder.Default
    private PaymentMethod paymentMethod = PaymentMethod.CASH;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'PENDING'")
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    // nullable — chỉ có khi thanh toán online
    @Column(name = "transaction_code", unique = true)
    private String transactionCode;

    // nullable — chỉ có khi đã thanh toán
    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}