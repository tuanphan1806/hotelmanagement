package com.hotel.backend.service;

import com.hotel.backend.entity.*;
import com.hotel.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatBotService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final FacilityRepository facilityRepository;
    private final GalleryRepository galleryRepository;

@Value("${gemini.api.key:}")
private String apiKey;

    private final WebClient.Builder webClientBuilder;

    public String ask(String question) {

        String context = buildHotelContext();

        String prompt = """
                Bạn là trợ lý AI của khách sạn.

                QUY TẮC:

                - Luôn trả lời bằng tiếng Việt.
                - Chỉ trả lời các vấn đề liên quan khách sạn.
                - Trả lời thân thiện, lịch sự.
                - Dựa vào dữ liệu được cung cấp.

                DỮ LIỆU KHÁCH SẠN:

                %s

                CÂU HỎI:

                %s
                """.formatted(context, question);

        return callGemini(prompt);
    }

    private String buildHotelContext() {

        StringBuilder sb = new StringBuilder();

        sb.append("===== ROOM TYPES =====\n");

        roomTypeRepository.findAllWithFacilities()
                .forEach(rt -> {

                    sb.append("Loại phòng: ")
                            .append(rt.getTypeName())
                            .append("\n");

                    sb.append("Giá: ")
                            .append(rt.getPrice())
                            .append("\n");

                    sb.append("Mô tả: ")
                            .append(rt.getDescription())
                            .append("\n");

                    sb.append("Tiện ích: ");

                    rt.getFacilities().forEach(
                            facility ->
                                    sb.append(
                                            facility.getFacilityName()
                                    ).append(", ")
                    );

                    sb.append("\n\n");
                });

        sb.append("===== ROOMS =====\n");

        roomRepository.findAll()
                .forEach(room -> {

                    sb.append("Tên phòng: ")
                            .append(room.getRoomName())
                            .append("\n");

                    sb.append("Tầng: ")
                            .append(room.getFloor())
                            .append("\n");

                    sb.append("Trạng thái: ")
                            .append(room.getStatus())
                            .append("\n");

                    sb.append("Loại phòng: ")
                            .append(
                                    room.getRoomType()
                                            .getTypeName()
                            )
                            .append("\n\n");
                });

        sb.append("===== FACILITIES =====\n");

        facilityRepository.findAll()
                .forEach(f -> {

                    sb.append(f.getFacilityName())
                            .append(" - ")
                            .append(f.getDescription())
                            .append("\n");
                });

        sb.append("\n===== GALLERY =====\n");

        galleryRepository.findAll()
                .forEach(g -> {

                    sb.append(g.getTitle())
                            .append(" - ")
                            .append(g.getType())
                            .append("\n");
                });

        return sb.toString();
    }

    private String callGemini(String prompt) {

        WebClient client =
                webClientBuilder
                        .baseUrl(
                                "https://generativelanguage.googleapis.com"
                        )
                        .build();

        Map<String, Object> request =
                Map.of(
                        "contents",
                        List.of(
                                Map.of(
                                        "parts",
                                        List.of(
                                                Map.of(
                                                        "text",
                                                        prompt
                                                )
                                        )
                                )
                        )
                );

        Map response =
                client.post()
                        .uri(
                                "/v1beta/models/gemini-2.5-flash:generateContent?key="
                                        + apiKey
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();

        try {

            List candidates =
                    (List) response.get("candidates");

            Map candidate =
                    (Map) candidates.get(0);

            Map content =
                    (Map) candidate.get("content");

            List parts =
                    (List) content.get("parts");

            Map part =
                    (Map) parts.get(0);

            return part.get("text")
                    .toString();

        } catch (Exception e) {

            return "Xin lỗi, tôi chưa thể trả lời câu hỏi này.";
        }
    }
}