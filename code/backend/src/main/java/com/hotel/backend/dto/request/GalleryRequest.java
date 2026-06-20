package com.hotel.backend.dto.request;
import lombok.Data;

@Data
public class GalleryRequest {
    private Long roomId;
    private String imageUrl;
}
