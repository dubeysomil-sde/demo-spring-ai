package com.demo.demospringai.service;

import com.demo.demospringai.DemoSpringAiApplication;
import com.demo.demospringai.payload.CricketResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = DemoSpringAiApplication.class)
class ChatServiceTest {

    @Autowired
    private ChatService chatService;

    @Test
    void testCricketResponse() {

        CricketResponse response =
                chatService.getCricketResponse(
                        "Who won the 2023 Cricket World Cup?"
                );

        System.out.println("================================");
        System.out.println("LLM RESPONSE:");
        System.out.println(response.getContent());
        System.out.println("================================");

        assertNotNull(response);
        assertNotNull(response.getContent());
    }

    @Test
    void testGetCricketPrompt() throws IOException {
        String cricketPrompt = chatService.getPrompt("prompts/Cricket_bot_prompts.txt");
        System.out.println("This is cricket prompt : " + cricketPrompt);
    }

    @Test
    void testPutValuesInPrompt() throws IOException {
        String cricketPrompt = chatService.getPrompt("prompts/Cricket_bot_prompts.txt");
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("inputText","how is sachin tenduldar related to virat kohli");
        String valuesInPrompt = chatService.putValuesInPrompt(cricketPrompt, valueMap);
        System.out.println(valuesInPrompt);
    }
}