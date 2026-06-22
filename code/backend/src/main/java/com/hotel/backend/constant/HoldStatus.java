package com.hotel.backend.constant;

public enum HoldStatus {
    ACTIVE,     // đang giữ chỗ (trong 15 phút)
    EXPIRED,    // hết giờ, ShedLock job tự động đổi
    CONVERTED,  // thanh toán thành công → thành Reservation
    RELEASED    // user hủy chủ động
}