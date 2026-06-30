package com.hotel.backend.repository;

import com.hotel.backend.entity.PaymentTransaction;
import com.hotel.backend.constant.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, String> {

    Optional<PaymentTransaction> findByTxnRef(String txnRef);

    List<PaymentTransaction> findByBookingId(String bookingId);

    List<PaymentTransaction> findByBookingIdAndStatus(String bookingId, PaymentStatus status);

    Optional<PaymentTransaction> findByProviderTxnId(String providerTxnId);
}
