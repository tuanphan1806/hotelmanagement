package com.hotel.backend.dto.request;
import com.hotel.backend.constant.IdCardType;
import lombok.Data;
import java.time.LocalDate;

@Data
public class GuestRequest {
    private Long reservationRoomId;
    private String fullName;
    private String phone;
    private String email;
    private String idCardNumber;
    private IdCardType idCardType;
    private LocalDate dateOfBirth;
    private String nationality;
    private Boolean isPrimary;
}
