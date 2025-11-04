package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class AIHandler {
    private final ChatClient chatClient;

    @PostMapping
    public String chat(@RequestBody String message){
        return chatClient.prompt().user(message).call().content();
    }

}
