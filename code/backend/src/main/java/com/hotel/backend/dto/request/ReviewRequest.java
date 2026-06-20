package com.hotel.backend.dto.request;
import lombok.Data;

@Data
public class ReviewRequest {
    private Long userId;
    private Long roomId;
    private Long reservationId;
    private Integer rating;
    private String comment;
}