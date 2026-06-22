package com.hotel.backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Payload gửi lên khi tạo mới / cập nhật Facility.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityRequest {

    @NotBlank(message = "Tên tiện nghi không được để trống")
    @Size(max = 255, message = "Tên tiện nghi tối đa 255 ký tự")
    private String facilityName;

    @Size(max = 255, message = "Loại tiện nghi tối đa 255 ký tự")
    private String type;

    private String description;

    @Size(max = 255, message = "Đường dẫn icon tối đa 255 ký tự")
    private String icon;
}