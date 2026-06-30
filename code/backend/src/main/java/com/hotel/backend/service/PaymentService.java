package com.hotel.backend.service;
import com.hotel.backend.dto.request.PaymentRequest;
import com.hotel.backend.dto.request.RefundRequest;
import com.hotel.backend.dto.response.PaymentResponse;

import com.hotel.backend.entity.PaymentTransaction;
import com.hotel.backend.constant.PaymentProvider;
import com.hotel.backend.constant.PaymentStatus;
import com.hotel.backend.repository.PaymentTransactionRepository;
import com.hotel.backend.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final VNPayService vnPayService;
    private final PaymentTransactionRepository transactionRepository;

    // ==================== TẠO GIAO DỊCH MỚI ====================

    @Transactional
    public PaymentResponse createPayment(PaymentRequest request, HttpServletRequest httpRequest) {
        String ipAddress = VNPayUtil.getClientIp(httpRequest);
        String txnRef = VNPayUtil.generateTxnRef(request.getBookingId());
        String orderInfo = request.getOrderInfo() != null
                ? request.getOrderInfo()
                : "Thanh toan dat phong " + request.getBookingId();

        // Lưu giao dịch PENDING vào DB trước
        PaymentTransaction transaction = PaymentTransaction.builder()
                .bookingId(request.getBookingId())
                .txnRef(txnRef)
                .provider(request.getProvider())
                .status(PaymentStatus.PENDING)
                .amount(request.getAmount())
                .currency("VND")
                .orderInfo(orderInfo)
                .ipAddress(ipAddress)
                .build();

        transaction = transactionRepository.save(transaction);
        log.info("Tạo giao dịch mới: txnRef={}, provider={}, amount={}",
                txnRef, request.getProvider(), request.getAmount());

        // Tạo URL thanh toán theo provider
        String paymentUrl = switch (request.getProvider()) {
            case VNPAY -> vnPayService.createPaymentUrl(transaction, ipAddress);
            // case MOMO -> moMoService.createPaymentUrl(transaction);
            // case ZALOPAY -> zaloPayService.createPaymentUrl(transaction);
            default -> throw new IllegalArgumentException("Provider không hỗ trợ: " + request.getProvider());
        };

        return PaymentResponse.builder()
                .transactionId(transaction.getId())
                .bookingId(transaction.getBookingId())
                .provider(transaction.getProvider())
                .status(transaction.getStatus())
                .amount(transaction.getAmount())
                .paymentUrl(paymentUrl)
                .message("Tạo giao dịch thành công")
                .createdAt(transaction.getCreatedAt())
                .build();
    }

    // ==================== TRUY VẤN ====================

    public PaymentTransaction getTransaction(String transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giao dịch: " + transactionId));
    }

    public List<PaymentTransaction> getTransactionsByBooking(String bookingId) {
        return transactionRepository.findByBookingId(bookingId);
    }

    public List<PaymentTransaction> getSuccessfulTransactions(String bookingId) {
        return transactionRepository.findByBookingIdAndStatus(bookingId, PaymentStatus.SUCCESS);
    }

    // ==================== HOÀN TIỀN ====================

    @Transactional
    public PaymentResponse refund(RefundRequest request) {
        PaymentTransaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giao dịch: " + request.getTransactionId()));

        // Kiểm tra trạng thái
        if (transaction.getStatus() != PaymentStatus.SUCCESS) {
            throw new RuntimeException("Chỉ có thể hoàn tiền giao dịch đã thành công");
        }
        if (request.getAmount() > transaction.getAmount()) {
            throw new RuntimeException("Số tiền hoàn không được lớn hơn số tiền đã thanh toán");
        }

        log.info("Xử lý hoàn tiền: txnRef={}, amount={}", transaction.getTxnRef(), request.getAmount());

        // Cập nhật trạng thái
        transaction.setStatus(PaymentStatus.REFUND_PENDING);
        transaction.setMessage("Đang xử lý hoàn tiền: " + request.getReason());
        transactionRepository.save(transaction);

        // Gọi API hoàn tiền theo provider
        switch (transaction.getProvider()) {
            case VNPAY -> vnPayService.refund(transaction, request.getAmount(), request.getReason());
            // case MOMO -> moMoService.refund(...)
            // case ZALOPAY -> zaloPayService.refund(...)
        }

        return PaymentResponse.builder()
                .transactionId(transaction.getId())
                .bookingId(transaction.getBookingId())
                .provider(transaction.getProvider())
                .status(transaction.getStatus())
                .amount(request.getAmount())
                .message("Yêu cầu hoàn tiền đã được gửi")
                .build();
    }
}
