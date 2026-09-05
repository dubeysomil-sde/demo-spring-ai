package com.demo.demospringai.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatModel chatModel;

    @GetMapping("/ai/generate")
    public String generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return chatModel.call(message);
    }

    // Streaming response (Recommended for LLMs)
    @GetMapping(value = "/ai/stream", produces = "text/event-stream")
    public Flux<String> stream(@RequestParam(value = "message") String message) {
        return chatModel.stream(message);
    }
}
