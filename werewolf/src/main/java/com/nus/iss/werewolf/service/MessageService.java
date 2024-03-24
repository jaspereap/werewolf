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

    public boolean publishToGameWithAck(String gameName, String data, MessageType type, int playerCount) {
        // Initialise acknowledgement
        ackService.initAck(gameName, type, playerCount);
        // Broadcast to clients
        webSocketService.publishToTopic(gameName, data, type);
        // Retrieve ack from clients
        boolean allAcksReceived = ackService.waitForAcknowledgments(gameName, type, 30, TimeUnit.SECONDS);
        if (allAcksReceived) {
            System.out.println("All Players Acknowledged: " + type.toString());
            return true;
        }
        ackService.clearAcknowledgment(gameName, type);
        System.out.println("Failed to Acknowledged in time");
        return false;
    }

    public void publishToPlayer(String gameName, String playerName, String data, MessageType type) {
        webSocketService.publishToTopic("%s/%s".formatted(gameName, playerName), data, type);
    }

}