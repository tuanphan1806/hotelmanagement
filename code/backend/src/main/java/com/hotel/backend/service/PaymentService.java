package com.hotel.backend.service;
import com.hotel.backend.dto.request.PaymentRequest;
import com.hotel.backend.dto.request.RefundRequest;
import com.hotel.backend.dto.response.PaymentResponse;

import com.hotel.backend.entity.PaymentTransaction;
import com.hotel.backend.entity.Reservation;
import com.hotel.backend.entity.User;
import com.hotel.backend.exception.AppException;
import com.hotel.backend.exception.ErrorCode;
import com.hotel.backend.constant.PaymentProvider;
import com.hotel.backend.constant.PaymentStatus;
import com.hotel.backend.constant.ReservationStatus;
import com.hotel.backend.constant.UserType;
import com.hotel.backend.repository.PaymentTransactionRepository;
import com.hotel.backend.repository.ReservationRepository;
import com.hotel.backend.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final VNPayService vnPayService;
    private final PaymentTransactionRepository transactionRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    // ==================== TẠO GIAO DỊCH MỚI ====================

    @Transactional
    public PaymentResponse createPayment(PaymentRequest request, HttpServletRequest httpRequest, User currentUser) {
        String ipAddress = VNPayUtil.getClientIp(httpRequest);
        String txnRef = VNPayUtil.generateTxnRef(String.valueOf(request.getBookingId()));
        Reservation reservation = reservationRepository.findById(request.getBookingId())
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND));
        ensureCanAccessReservation(currentUser, reservation);
        validateReservationCanAcceptPayment(reservation);
        validateDraftDepositPaymentNotCreated(reservation);
        validatePaymentAmount(reservation, request.getAmount());

        if (request.getProvider() == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST, "provider không được để trống");
        }
        if (request.getProvider() == PaymentProvider.CASH) {
            throw new AppException(ErrorCode.INVALID_REQUEST,
                    "Thanh toán tiền mặt vui lòng dùng endpoint /api/payments/cash");
        }
        String orderInfo = request.getOrderInfo() != null
                ? request.getOrderInfo()
                : "Thanh toan dat phong " + request.getBookingId();

        // Lưu giao dịch PENDING vào DB trước
        PaymentTransaction transaction = PaymentTransaction.builder()
                .reservation(reservation)
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
                .bookingId(transaction.getReservation().getId())
                .provider(transaction.getProvider())
                .status(transaction.getStatus())
                .amount(transaction.getAmount())
                .paymentUrl(paymentUrl)
                .message("Tạo giao dịch thành công")
                .createdAt(transaction.getCreatedAt())
                .build();
    }

    @Transactional
    public PaymentResponse createCashPayment(PaymentRequest request, HttpServletRequest httpRequest) {
        String ipAddress = VNPayUtil.getClientIp(httpRequest);
        Reservation reservation = reservationRepository.findById(request.getBookingId())
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND));
        validateReservationCanAcceptPayment(reservation);
        validatePaymentAmount(reservation, request.getAmount());

        String txnRef = VNPayUtil.generateTxnRef("CASH-" + request.getBookingId());
        String orderInfo = request.getOrderInfo() != null
                ? request.getOrderInfo()
                : "Thanh toan tien mat dat phong " + request.getBookingId();

        PaymentTransaction transaction = PaymentTransaction.builder()
                .reservation(reservation)
                .txnRef(txnRef)
                .provider(PaymentProvider.CASH)
                .status(PaymentStatus.SUCCESS)
                .amount(request.getAmount())
                .currency("VND")
                .orderInfo(orderInfo)
                .ipAddress(ipAddress)
                .paidAt(LocalDateTime.now())
                .message("Thanh toán tiền mặt thành công")
                .build();

        transaction = transactionRepository.save(transaction);
        reservationService.convertHoldsAfterPayment(reservation.getId());

        log.info("Tạo giao dịch tiền mặt: txnRef={}, reservationId={}, amount={}",
                txnRef, reservation.getId(), request.getAmount());

        return PaymentResponse.builder()
                .transactionId(transaction.getId())
                .bookingId(transaction.getReservation().getId())
                .provider(transaction.getProvider())
                .status(transaction.getStatus())
                .amount(transaction.getAmount())
                .message("Thanh toán tiền mặt thành công")
                .createdAt(transaction.getCreatedAt())
                .build();
    }

    // ==================== TRUY VẤN ====================

    public PaymentTransaction getTransaction(String transactionId, User currentUser) {
        PaymentTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Không tìm thấy giao dịch: " + transactionId));
        ensureCanAccessReservation(currentUser, transaction.getReservation());
        return transaction;
    }

    public List<PaymentTransaction> getTransactionsByReservation(Long reservationId, User currentUser) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND));
        ensureCanAccessReservation(currentUser, reservation);
        return transactionRepository.findByReservationId(reservationId);
    }
 
    public List<PaymentTransaction> getSuccessfulTransactions(Long reservationId) {
        return transactionRepository.findByReservationIdAndStatus(reservationId, PaymentStatus.SUCCESS);
    }
    // ==================== HOÀN TIỀN ====================

    @Transactional
    public PaymentResponse refund(RefundRequest request) {
        PaymentTransaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Không tìm thấy giao dịch: " + request.getTransactionId()));

        if (transaction.getReservation().getStatus() != ReservationStatus.CANCELLED) {
            throw new AppException(ErrorCode.INVALID_REQUEST,
                    "Chỉ có thể hoàn tiền cho đặt phòng đã hủy");
        }

        // Kiểm tra trạng thái
        if (transaction.getStatus() != PaymentStatus.SUCCESS) {
            throw new AppException(ErrorCode.INVALID_REQUEST,
                    "Chỉ có thể hoàn tiền giao dịch đã thành công");
        }
        if (request.getAmount() > transaction.getAmount()) {
            throw new AppException(ErrorCode.INVALID_REQUEST,
                    "Số tiền hoàn không được lớn hơn số tiền đã thanh toán");
        }
        if (!request.getAmount().equals(transaction.getAmount())) {
            throw new AppException(ErrorCode.INVALID_REQUEST,
                    "Hiện chỉ hỗ trợ hoàn toàn bộ số tiền của giao dịch");
        }

        log.info("Xử lý hoàn tiền: txnRef={}, amount={}", transaction.getTxnRef(), request.getAmount());

        // Cập nhật trạng thái
        transaction.setStatus(PaymentStatus.REFUND_PENDING);
        transaction.setMessage("Đang xử lý hoàn tiền: " + request.getReason());
        transactionRepository.save(transaction);

        // Gọi API hoàn tiền theo provider
        String message = switch (transaction.getProvider()) {
            case VNPAY -> {
                vnPayService.refund(transaction, request.getAmount(), request.getReason());
                yield "Yêu cầu hoàn tiền VNPay đã được gửi";
            }
            case CASH -> "Yêu cầu hoàn tiền tiền mặt đang chờ xử lý tại quầy";
        };

        return PaymentResponse.builder()
                .transactionId(transaction.getId())
                .bookingId(transaction.getReservation().getId())
                .provider(transaction.getProvider())
                .status(transaction.getStatus())
                .amount(request.getAmount())
                .message(message)
                .build();
    }

    private void validateReservationCanAcceptPayment(Reservation reservation) {
        if (List.of(ReservationStatus.CANCELLED, ReservationStatus.CHECKED_OUT)
                .contains(reservation.getStatus())) {
            throw new AppException(ErrorCode.INVALID_REQUEST,
                    "Không thể thanh toán cho đặt phòng đã hủy hoặc đã checkout");
        }
    }

    private void validateDraftDepositPaymentNotCreated(Reservation reservation) {
        if (reservation.getStatus() != ReservationStatus.DRAFT) {
            return;
        }

        boolean hasDepositPayment = transactionRepository.existsByReservationIdAndStatusIn(
                reservation.getId(),
                List.of(PaymentStatus.PENDING, PaymentStatus.SUCCESS));

        if (hasDepositPayment) {
            throw new AppException(ErrorCode.INVALID_REQUEST,
                    "Đặt phòng này đã có giao dịch đặt cọc, không thể tạo thêm giao dịch cọc");
        }
    }

    private void validatePaymentAmount(Reservation reservation, Long amount) {
        if (amount == null || amount <= 0) {
            throw new AppException(ErrorCode.INVALID_REQUEST, "Số tiền thanh toán không hợp lệ");
        }

        long totalAmount = reservation.getTotalAmount().longValue();
        long paidAmount = getPaidAmount(reservation.getId());
        long remainingAmount = Math.max(0, totalAmount - paidAmount);

        if (remainingAmount <= 0) {
            throw new AppException(ErrorCode.INVALID_REQUEST, "Đặt phòng đã thanh toán đủ");
        }
        if (amount > remainingAmount) {
            throw new AppException(ErrorCode.INVALID_REQUEST,
                    String.format("Số tiền thanh toán vượt quá số còn lại %,d VND", remainingAmount));
        }
    }

    private long getPaidAmount(Long reservationId) {
        Long paidAmount = transactionRepository.sumSuccessAmountByReservationId(reservationId);
        return paidAmount != null ? paidAmount : 0L;
    }

    private void ensureCanAccessReservation(User currentUser, Reservation reservation) {
        if (currentUser == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST, "Bạn cần đăng nhập để thực hiện thao tác này");
        }
        if (List.of(UserType.ADMIN, UserType.STAFF).contains(currentUser.getType())) {
            return;
        }
        if (reservation.getCustomer() == null
                || !reservation.getCustomer().getId().equals(currentUser.getId())) {
            throw new AppException(ErrorCode.INVALID_REQUEST,
                    "Bạn không có quyền thao tác với đặt phòng này");
        }
    }
}
