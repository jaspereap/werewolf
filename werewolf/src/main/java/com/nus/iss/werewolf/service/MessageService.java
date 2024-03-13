package com.nus.iss.werewolf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Service
public class MessageService {
    @Autowired
    private SimpMessagingTemplate msgTemplate;

    final String topic = "/topic/";

    public void publishToGame(String gameName, String data) {
        String destination = topic + gameName;
        // System.out.println(destination);
        System.out.println("Outbound data: " + data);
        msgTemplate.convertAndSend(destination, data);
    }
    // e.g "type": "GAME_INIT", "data": "..."
    public JsonObject addType(MessageType type, JsonObject json) {
        return Json.createObjectBuilder()
                    .add("type", type.toString())
                    .add("data", json)
                    .build();
    }
}
