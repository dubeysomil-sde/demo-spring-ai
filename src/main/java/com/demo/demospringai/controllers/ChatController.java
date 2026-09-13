package com.demo.demospringai.controllers;

import com.demo.demospringai.controllers.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;


@Controller
@RequiredArgsConstructor
@AllArgsConstructor
public class ChatController {

    @Autowired
    ChatService chatService;

    @GetMapping("/ai/generate")
    public ResponseEntity<String> generate(@RequestParam(value = "prompt", defaultValue = "Tell me a joke") String prompt) {

        String response = chatService.generateResponse(prompt);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ai/generate/stream")
    public Flux<String> generateStream(@RequestParam(value = "prompt", defaultValue = "Tell me a joke") String prompt) {

        Flux<String> response = chatService.generateStreamResponse(prompt);
        return response;
    }
}
