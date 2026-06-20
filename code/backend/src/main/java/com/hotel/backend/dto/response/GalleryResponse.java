package com.hotel.backend.dto.response;
import lombok.Data;


@Data
public class GalleryResponse {
    private Long id;
    private Long roomId;
    private String imageUrl;
}
