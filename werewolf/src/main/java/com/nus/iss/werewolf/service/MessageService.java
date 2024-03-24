package com.nus.iss.werewolf.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired private WebSocketService webSocketService;
    @Autowired private AckService ackService;

    public void publishToGame(String gameId, String data, MessageType type) {
        webSocketService.publishToTopic(gameId, data, type);
    }

    public boolean publishToGameWithAck(String gameId, String data, MessageType type, int playerCount) {
        // Initialise acknowledgement
        ackService.initAck(gameId, type, playerCount);
        // Broadcast to clients
        webSocketService.publishToTopic(gameId, data, type);
        // Retrieve ack from clients
        boolean allAcksReceived = ackService.waitForAcknowledgments(gameId, type, 30, TimeUnit.SECONDS);
        if (allAcksReceived) {
            System.out.println("All Players Acknowledged: " + type.toString());
            return true;
        }
        ackService.clearAcknowledgment(gameId, type);
        System.out.println("Failed to Acknowledged in time");
        return false;
    }

    public void publishToPlayer(String gameId, String playerName, String data, MessageType type) {
        webSocketService.publishToTopic("%s/%s".formatted(gameId, playerName), data, type);
    }

}