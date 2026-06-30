package com.hotel.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // ── Generic ──────────────────────────────────────────────
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REQUEST(4000, "Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND(4004, "Không tìm thấy tài nguyên", HttpStatus.NOT_FOUND),
    DUPLICATE_RESOURCE(4009, "Tài nguyên đã tồn tại", HttpStatus.CONFLICT),

    // ── Reservation ──────────────────────────────────────────
    RESERVATION_NOT_FOUND(5001, "Không tìm thấy đặt phòng", HttpStatus.NOT_FOUND),
    RESERVATION_CODE_DUPLICATE(5002, "Mã đặt phòng đã tồn tại", HttpStatus.CONFLICT),
    RESERVATION_INVALID_DATE(5003, "Ngày check-in phải trước ngày check-out", HttpStatus.BAD_REQUEST),
    RESERVATION_CHECKIN_PAST(5004, "Ngày check-in không được ở quá khứ", HttpStatus.BAD_REQUEST),
    RESERVATION_CANNOT_CANCEL(5005, "Không thể hủy đặt phòng ở trạng thái này", HttpStatus.BAD_REQUEST),
    RESERVATION_CANNOT_CONFIRM(5006, "Không thể xác nhận đặt phòng ở trạng thái này", HttpStatus.BAD_REQUEST),
    RESERVATION_CANNOT_UPDATE(5009, "Chỉ có thể cập nhật đặt phòng ở trạng thái DRAFT", HttpStatus.BAD_REQUEST),
    // ── Room availability ─────────────────────────────────────
    ROOM_TYPE_NOT_FOUND(5010, "Không tìm thấy loại phòng", HttpStatus.NOT_FOUND),
    ROOM_NOT_AVAILABLE(5011, "Loại phòng không đủ số lượng trong khoảng ngày yêu cầu", HttpStatus.CONFLICT),
    ROOM_QUANTITY_INVALID(5012, "Số lượng phòng phải lớn hơn 0", HttpStatus.BAD_REQUEST),

    // ── RoomHold ─────────────────────────────────────────────
    ROOM_HOLD_NOT_FOUND(5020, "Không tìm thấy giữ chỗ", HttpStatus.NOT_FOUND),
    ROOM_HOLD_EXPIRED(5021, "Giữ chỗ đã hết hạn, vui lòng đặt phòng lại", HttpStatus.GONE),
    ROOM_HOLD_ALREADY_EXISTS(5022, "Loại phòng này đã có giữ chỗ đang hoạt động", HttpStatus.CONFLICT),

    // ── ReservationRoom (assign) ─────────────────────────────
    RESERVATION_ROOM_NOT_FOUND(5030, "Không tìm thấy phòng trong đặt chỗ", HttpStatus.NOT_FOUND),
    ROOM_NOT_FOUND(5031, "Không tìm thấy phòng", HttpStatus.NOT_FOUND),
    ROOM_ALREADY_ASSIGNED(5032, "Phòng đã được gán cho đặt chỗ này", HttpStatus.CONFLICT),
    ROOM_WRONG_TYPE(5033, "Phòng không thuộc loại phòng yêu cầu", HttpStatus.BAD_REQUEST),

    RESERVATION_CANNOT_CHECKIN(5007, "Chỉ có thể check-in khi đặt phòng đã CONFIRMED", HttpStatus.BAD_REQUEST),
    RESERVATION_CANNOT_CHECKOUT(5008, "Chỉ có thể check-out khi đang CHECKED_IN", HttpStatus.BAD_REQUEST),

    // ── Customer / User ──────────────────────────────────────
    CUSTOMER_NOT_FOUND(5040, "Không tìm thấy khách hàng", HttpStatus.NOT_FOUND),
    ;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code       = code;
        this.message    = message;
        this.httpStatus = httpStatus;
    }
}