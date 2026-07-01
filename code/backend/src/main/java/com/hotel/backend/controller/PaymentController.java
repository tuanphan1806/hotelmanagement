package com.hotel.backend.controller;

import com.hotel.backend.dto.request.PaymentRequest;
import com.hotel.backend.dto.response.PaymentResponse;
import com.hotel.backend.dto.request.RefundRequest;
import com.hotel.backend.entity.PaymentTransaction;
import com.hotel.backend.service.PaymentService;
import com.hotel.backend.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final VNPayService vnPayService;

    // ==================== TẠO GIAO DỊCH ====================

    /**
     * POST /api/payments/create
     * NextJS gọi endpoint này để lấy paymentUrl
     */
    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody PaymentRequest request,
            HttpServletRequest httpRequest) {

        log.info("Yêu cầu tạo thanh toán: bookingId={}, provider={}, amount={}",
                request.getBookingId(), request.getProvider(), request.getAmount());

        PaymentResponse response = paymentService.createPayment(request, httpRequest);
        return ResponseEntity.ok(response);
    }

    // ==================== VNPAY CALLBACKS ====================

    /**
     * GET /api/payments/vnpay/return
     * VNPay redirect khách hàng về đây sau khi thanh toán (qua browser)
     */
    @GetMapping("/vnpay/return")
    public void vnpayReturn(
            @RequestParam Map<String, String> params,
            HttpServletResponse response) throws IOException {

        log.info("VNPay Return URL được gọi");

        try {
            PaymentTransaction transaction = vnPayService.handleReturn(params);
            String status = transaction.getStatus().name().toLowerCase();
            String bookingId = String.valueOf(transaction.getReservation().getId());

            // Redirect về trang kết quả NextJS
            String redirectUrl = String.format(
                    "http://localhost:3000/booking/payment-result?status=%s&bookingId=%s&txnRef=%s",
                    status, bookingId, transaction.getTxnRef()
            );
            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            log.error("Lỗi xử lý VNPay Return: {}", e.getMessage());
            response.sendRedirect("http://localhost:3000/booking/payment-result?status=error");
        }
    }

    /**
     * GET /api/payments/vnpay/ipn
     * VNPay server gọi endpoint này để xác nhận giao dịch (server-to-server)
     * Đây là endpoint QUAN TRỌNG NHẤT để cập nhật DB
     */
    @GetMapping("/vnpay/ipn")
    public ResponseEntity<Map<String, String>> vnpayIPN(
            @RequestParam Map<String, String> params) {

        log.info("VNPay IPN được gọi");
        Map<String, String> result = vnPayService.handleIPN(params);
        return ResponseEntity.ok(result);
    }

    // ==================== TRUY VẤN GIAO DỊCH ====================

    /**
     * GET /api/payments/{transactionId}
     * Lấy thông tin một giao dịch
     */
    @GetMapping("/{transactionId}")
    public ResponseEntity<PaymentTransaction> getTransaction(@PathVariable String transactionId) {
        return ResponseEntity.ok(paymentService.getTransaction(transactionId));
    }

    /**
     * GET /api/payments/booking/{bookingId}
     * Lấy tất cả giao dịch của một booking
     */
    @GetMapping("/booking/{reservationId}")
    public ResponseEntity<List<PaymentTransaction>> getByBooking(@PathVariable Long reservationId) {
        return ResponseEntity.ok(paymentService.getTransactionsByReservation(reservationId));
    }

    // ==================== HOÀN TIỀN ====================

    /**
     * POST /api/payments/refund
     * Yêu cầu hoàn tiền
     */
    @PostMapping("/refund")
    public ResponseEntity<PaymentResponse> refund(@Valid @RequestBody RefundRequest request) {
        log.info("Yêu cầu hoàn tiền: transactionId={}, amount={}",
                request.getTransactionId(), request.getAmount());
        return ResponseEntity.ok(paymentService.refund(request));
    }
}
