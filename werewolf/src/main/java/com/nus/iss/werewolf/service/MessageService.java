package com.nus.iss.werewolf.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired private WebSocketService webSocketService;
    @Autowired private AckService ackService;

    public void publishToGame(String gameName, String data, MessageType type) {
        webSocketService.publishToTopic(gameName, data, type);
    }

    public void publishToGameWithAck(String gameName, String data, MessageType type, int playerCount) {
        ackService.initAck(gameName, playerCount);
        webSocketService.publishToTopic(gameName, data, type);
        boolean allAcksReceived = ackService.waitForAcknowledgments(gameName, 30, TimeUnit.SECONDS);
        if (allAcksReceived) {
            System.out.println("All Players Acknowledged");
        } else {
            System.out.println("Ack Failed");
        }
        ackService.clearAcknowledgment(gameName);
    }

    public void publishToPlayer(String gameName, String playerName, String data, MessageType type) {
        webSocketService.publishToTopic("%s/%s".formatted(gameName, playerName), data, type);
    }
}