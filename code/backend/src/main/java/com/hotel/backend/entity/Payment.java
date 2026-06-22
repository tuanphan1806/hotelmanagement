package com.hotel.backend.entity;

import com.hotel.backend.constant.PaymentMethod;
import com.hotel.backend.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Payment extends AbstractEntity<Long> implements Serializable{


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id",unique = true, nullable = false)
    private Reservation reservation;

    @Column(precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod = PaymentMethod.CASH;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name = "transaction_code")
    private String transactionCode;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;


}
