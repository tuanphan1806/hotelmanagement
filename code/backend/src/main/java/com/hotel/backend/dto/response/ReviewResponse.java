package com.hotel.backend.dto.response;
import lombok.Data;

@Data
public class ReviewResponse {
    private Long id;
    private Long userId;
    private String userName;
    private Long roomId;
    private String roomName;
    private Integer rating;
    private String comment;
}
