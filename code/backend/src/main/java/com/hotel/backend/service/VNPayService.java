package com.hotel.backend.service;

import com.hotel.backend.config.VNPayConfig;
import com.hotel.backend.entity.PaymentTransaction;
import com.hotel.backend.constant.PaymentStatus;
import com.hotel.backend.repository.PaymentTransactionRepository;
import com.hotel.backend.util.VNPayUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VNPayService {

    private final VNPayConfig vnPayConfig;
    private final PaymentTransactionRepository transactionRepository;
    private final ReservationService reservationService;
    // ==================== TẠO URL THANH TOÁN ====================

    /**
     * Tạo URL redirect sang trang thanh toán VNPay
     */
    public String createPaymentUrl(PaymentTransaction transaction, String ipAddress) {
        String txnRef = transaction.getTxnRef();
        long amount = transaction.getAmount();
        String orderInfo = transaction.getOrderInfo() != null
                ? transaction.getOrderInfo()
                : "Thanh toan dat phong " + transaction.getReservation().getId();

        // Thời gian tạo và hết hạn giao dịch (15 phút)
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String createDate = formatter.format(calendar.getTime());
        calendar.add(Calendar.MINUTE, 15);
        String expireDate = formatter.format(calendar.getTime());

        // Tham số gửi sang VNPay (phải sort theo alphabet)
        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", vnPayConfig.getVersion());
        vnpParams.put("vnp_Command", vnPayConfig.getCommand());
        vnpParams.put("vnp_TmnCode", vnPayConfig.getTmnCode());
        vnpParams.put("vnp_Amount", String.valueOf(amount * 100)); // VNPay nhân 100
        vnpParams.put("vnp_CurrCode", vnPayConfig.getCurrCode());
        vnpParams.put("vnp_TxnRef", txnRef);
        vnpParams.put("vnp_OrderInfo", orderInfo);
        vnpParams.put("vnp_OrderType", vnPayConfig.getOrderType());
        vnpParams.put("vnp_Locale", vnPayConfig.getLocale());
        vnpParams.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl());
        vnpParams.put("vnp_IpAddr", ipAddress);
        vnpParams.put("vnp_CreateDate", createDate);
        vnpParams.put("vnp_ExpireDate", expireDate);

        // Tạo chuỗi hash
        String queryString = vnpParams.entrySet().stream()
                .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.US_ASCII)
                        + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.US_ASCII))
                .collect(Collectors.joining("&"));

        String secureHash = VNPayUtil.hmacSHA512(vnPayConfig.getHashSecret(), queryString);
        String paymentUrl = vnPayConfig.getPaymentUrl() + "?" + queryString + "&vnp_SecureHash=" + secureHash;

        log.info("Tạo URL thanh toán VNPay cho txnRef={}, amount={}", txnRef, amount);
        return paymentUrl;
    }

    // ==================== XỬ LÝ CALLBACK (Return URL) ====================

    /**
     * Xử lý khi VNPay redirect khách hàng về sau thanh toán
     * Return URL: khách hàng thấy trang kết quả
     */
    public PaymentTransaction handleReturn(Map<String, String> params) {
        log.info("VNPay Return URL params: {}", params);

        // Xác thực chữ ký
        if (!verifySignature(params)) {
            log.error("VNPay Return: Chữ ký không hợp lệ");
            throw new RuntimeException("Chữ ký VNPay không hợp lệ");
        }

        String txnRef = params.get("vnp_TxnRef");
        String responseCode = params.get("vnp_ResponseCode");
        String providerTxnId = params.get("vnp_TransactionNo");
        String bankCode = params.get("vnp_BankCode");

        PaymentTransaction transaction = transactionRepository.findByTxnRef(txnRef)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giao dịch: " + txnRef));

        // Cập nhật trạng thái
        transaction.setResponseCode(responseCode);
        transaction.setProviderTxnId(providerTxnId);
        transaction.setBankCode(bankCode);

        if ("00".equals(responseCode)) {
            transaction.setStatus(PaymentStatus.SUCCESS);
            transaction.setPaidAt(LocalDateTime.now());
            transaction.setMessage("Thanh toán thành công");
            log.info("Thanh toán VNPay thành công: txnRef={}", txnRef);
            reservationService.convertHoldsAfterPayment(transaction.getReservation().getId());
        } else {
            transaction.setStatus(PaymentStatus.FAILED);
            transaction.setMessage(getVNPayMessage(responseCode));
            log.warn("Thanh toán VNPay thất bại: txnRef={}, code={}", txnRef, responseCode);
        }

        return transactionRepository.save(transaction);
    }

    // ==================== XỬ LÝ IPN (Server-to-Server) ====================

    /**
     * Xử lý IPN từ VNPay server gọi về (không qua browser)
     * Đây là nơi cập nhật DB chính xác nhất
     */
    public Map<String, String> handleIPN(Map<String, String> params) {
        log.info("VNPay IPN params: {}", params);

        Map<String, String> result = new HashMap<>();

        // Bước 1: Xác thực chữ ký
        if (!verifySignature(params)) {
            log.error("VNPay IPN: Chữ ký không hợp lệ");
            result.put("RspCode", "97");
            result.put("Message", "Invalid Checksum");
            return result;
        }

        String txnRef = params.get("vnp_TxnRef");
        String responseCode = params.get("vnp_ResponseCode");
        String providerTxnId = params.get("vnp_TransactionNo");
        long vnpAmount = Long.parseLong(params.get("vnp_Amount")) / 100; // VNPay gửi * 100

        // Bước 2: Tìm giao dịch
        Optional<PaymentTransaction> optTxn = transactionRepository.findByTxnRef(txnRef);
        if (optTxn.isEmpty()) {
            log.error("VNPay IPN: Không tìm thấy giao dịch txnRef={}", txnRef);
            result.put("RspCode", "01");
            result.put("Message", "Order not found");
            return result;
        }

        PaymentTransaction transaction = optTxn.get();

        // Bước 3: Kiểm tra số tiền
        if (vnpAmount != transaction.getAmount()) {
            log.error("VNPay IPN: Số tiền không khớp. Expected={}, Got={}", transaction.getAmount(), vnpAmount);
            result.put("RspCode", "04");
            result.put("Message", "Invalid Amount");
            return result;
        }

        // Bước 4: Kiểm tra trạng thái (tránh xử lý 2 lần)
        if (transaction.getStatus() != PaymentStatus.PENDING) {
            log.warn("VNPay IPN: Giao dịch đã được xử lý rồi. txnRef={}", txnRef);
            result.put("RspCode", "02");
            result.put("Message", "Order already confirmed");
            return result;
        }

        // Bước 5: Cập nhật DB
        transaction.setResponseCode(responseCode);
        transaction.setProviderTxnId(providerTxnId);
        transaction.setBankCode(params.get("vnp_BankCode"));

        if ("00".equals(responseCode)) {
            transaction.setStatus(PaymentStatus.SUCCESS);
            transaction.setPaidAt(LocalDateTime.now());
            transaction.setMessage("Thanh toán thành công");

            reservationService.convertHoldsAfterPayment(transaction.getReservation().getId());
        } else {
            transaction.setStatus(PaymentStatus.FAILED);
            transaction.setMessage(getVNPayMessage(responseCode));
        }

        transactionRepository.save(transaction);
        log.info("VNPay IPN xử lý xong: txnRef={}, status={}", txnRef, transaction.getStatus());

        result.put("RspCode", "00");
        result.put("Message", "Confirm Success");
        return result;
    }

    // ==================== HOÀN TIỀN ====================

    /**
     * Gọi API hoàn tiền VNPay
     */
    public Map<String, Object> refund(PaymentTransaction transaction, long refundAmount, String reason) {
        // TODO: Gọi VNPay Refund API
        // Tham khảo: https://sandbox.vnpayment.vn/apis/docs/truy-van-hoan-tien/querydr&refund.html
        // Cần thêm implementation khi đã có API URL từ VNPay

        log.info("Yêu cầu hoàn tiền VNPay: txnRef={}, amount={}", transaction.getTxnRef(), refundAmount);

        Map<String, Object> refundParams = new TreeMap<>();
        refundParams.put("vnp_RequestId", VNPayUtil.generateTxnRef("REFUND"));
        refundParams.put("vnp_Version", vnPayConfig.getVersion());
        refundParams.put("vnp_Command", "refund");
        refundParams.put("vnp_TmnCode", vnPayConfig.getTmnCode());
        refundParams.put("vnp_TransactionType", "02"); // 02 = hoàn toàn bộ, 03 = hoàn một phần
        refundParams.put("vnp_TxnRef", transaction.getTxnRef());
        refundParams.put("vnp_Amount", refundAmount * 100);
        refundParams.put("vnp_OrderInfo", reason != null ? reason : "Hoan tien dat phong");
        refundParams.put("vnp_TransactionNo", transaction.getProviderTxnId());
        refundParams.put("vnp_CreateBy", "hotel_system");

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        refundParams.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime()));
        refundParams.put("vnp_IpAddr", "127.0.0.1");

        return refundParams;
    }

    // ==================== PRIVATE HELPERS ====================

    /**
     * Xác thực chữ ký từ VNPay
     */
    private boolean verifySignature(Map<String, String> params) {
        String receivedHash = params.get("vnp_SecureHash");
        if (receivedHash == null) return false;

        // Loại bỏ các field hash trước khi tính lại
        Map<String, String> signParams = new TreeMap<>(params);
        signParams.remove("vnp_SecureHash");
        signParams.remove("vnp_SecureHashType");

        String queryString = signParams.entrySet().stream()
                .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.US_ASCII)
                        + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.US_ASCII))
                .collect(Collectors.joining("&"));

        String computedHash = VNPayUtil.hmacSHA512(vnPayConfig.getHashSecret(), queryString);
        boolean valid = computedHash.equalsIgnoreCase(receivedHash);

        if (!valid) {
            log.error("Chữ ký không khớp. Computed={}, Received={}", computedHash, receivedHash);
        }
        return valid;
    }

    /**
     * Chuyển mã lỗi VNPay sang thông báo tiếng Việt
     */
    private String getVNPayMessage(String responseCode) {
        return switch (responseCode) {
            case "00" -> "Giao dịch thành công";
            case "07" -> "Trừ tiền thành công. Giao dịch bị nghi ngờ (liên quan tới lừa đảo, giao dịch bất thường)";
            case "09" -> "Thẻ/Tài khoản chưa đăng ký dịch vụ InternetBanking";
            case "10" -> "Xác thực thông tin thẻ/tài khoản không đúng quá 3 lần";
            case "11" -> "Đã hết hạn chờ thanh toán. Vui lòng thực hiện lại giao dịch";
            case "12" -> "Thẻ/Tài khoản bị khóa";
            case "13" -> "Sai mật khẩu xác thực giao dịch (OTP)";
            case "24" -> "Khách hàng hủy giao dịch";
            case "51" -> "Tài khoản không đủ số dư";
            case "65" -> "Tài khoản đã vượt quá hạn mức giao dịch trong ngày";
            case "75" -> "Ngân hàng thanh toán đang bảo trì";
            case "79" -> "Sai mật khẩu thanh toán quá số lần quy định";
            default -> "Giao dịch thất bại. Mã lỗi: " + responseCode;
        };
    }
}