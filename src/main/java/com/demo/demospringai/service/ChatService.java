package com.demo.demospringai.service;

import com.demo.demospringai.payload.CricketResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Getter
@Setter
public class ChatService {

    private final ChatModel chatModel;

    public String generateResponse(String prompt) {
        return chatModel.call(prompt);
    }

    public Flux<String> generateStreamResponse(String prompt) {
        return chatModel.stream(prompt);
    }
    public CricketResponse getCricketResponse(String inputText) {
        String cricketPrompt = "As a cricket expert. Answer this question" + inputText +
                ". If the above question is not related to cricket, then Generate one joke saying this question is out of context." +
                ". Give me the JSON response and The JSON must have exactly this format:\n" +
                "        {\n" +
                "          \"content\": \"your response here\"\n" +
                "        }";

        ChatResponse chatResponse = chatModel.call(new Prompt(cricketPrompt));
        String responseString = chatResponse.getResult().getOutput().getText();
        ObjectMapper mapper = new ObjectMapper();
        CricketResponse cricketResponse = mapper.readValue(responseString, CricketResponse.class);
        return cricketResponse;
    }

    public List<String> generateImages(String imageDesc, String size, int number) throws IOException {
        String imageBot = this.getPrompt("image_bot");
        this.putValuesInPrompt(imageBot, Map.of(
                "numberOfImages", number + "",  "description", imageDesc, "size", size
        ));
    }

    public String getPrompt(String fileName) throws IOException {
        Path filePath = new ClassPathResource(fileName).getFile().toPath();
        return Files.readString(filePath);
    }

    public String putValuesInPrompt(String template, Map<String, String> variables){
        for(Map.Entry<String, String> entry: variables.entrySet()){
            template = template.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return template;
    }
}
