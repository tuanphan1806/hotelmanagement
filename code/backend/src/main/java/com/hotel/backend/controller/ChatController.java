package com.hotel.backend.controller;

import com.hotel.backend.dto.request.ChatRequest;
import com.hotel.backend.dto.response.ChatResponse;
import com.hotel.backend.service.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatBotService chatBotService;

    @PostMapping
    public ChatResponse chat(
            @RequestBody ChatRequest request
    ) {

        return ChatResponse.builder()
                .answer(
                        chatBotService.ask(
                                request.getQuestion()
                        )
                )
                .build();
    }
}