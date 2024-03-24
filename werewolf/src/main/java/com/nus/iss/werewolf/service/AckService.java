package com.nus.iss.werewolf.service;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
@Service
@CrossOrigin(origins = "*")
public class AckService {
    private final Map<List<String>, CountDownLatch> gameAckLatches = new ConcurrentHashMap<>();

    public void initAck(String gameName, MessageType type, int responseCount) {
        System.out.printf("initAck -> inject %s, %s into gameAckLatches\n", gameName, type.toString());
        List<String> key = toKey(gameName, type.toString());
        if (gameAckLatches.containsKey(key)) {
            clearAcknowledgment(gameName, type);
        }
        gameAckLatches.put(key, new CountDownLatch(responseCount));
        System.out.println(gameAckLatches);
    }

    public boolean hasGame(String gameName, String type) {
        return gameAckLatches.containsKey(toKey(gameName, type));
    }

    public boolean waitForAcknowledgments(String gameName, MessageType type, long timeout, TimeUnit unit) {
        System.out.println("Waiting for acks.........");
        CountDownLatch latch = gameAckLatches.get(toKey(gameName, type.toString()));
        System.out.println(gameAckLatches);

        try {
            // Returns true if the count reached zero and false if the waiting time elapsed before the count reached zero
            return latch.await(timeout, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            return false;
        }
    }

    public void acknowledgePlayer(String gameName, String type) {
        CountDownLatch latch = gameAckLatches.get(toKey(gameName, type));
        if (latch != null) {
            latch.countDown();
            // Optionally log the acknowledgment or notify other parts of the system
            System.out.println("");
            System.out.println(gameAckLatches);
        }
    }

    public void clearAcknowledgment(String gameName, MessageType type) {
        gameAckLatches.remove(toKey(gameName, gameName));
    }
    
    // @MessageMapping("/{gameName}/{playerName}/ack")
    // public void playerAck(@DestinationVariable String gameName, 
    //     @DestinationVariable String playerName, 
    //     @Payload String body, 
    //     SimpMessageHeaderAccessor header) {
    //     System.out.printf("\tInbound Ack: \n\tgameName: %s \n\tplayerName: %s \n\tBody: %s\n", gameName, playerName, body);
    //     System.out.println("\tInbound Headers: " + header.getFirstNativeHeader("type"));
    //     if (hasGame(gameName)) {
    //         acknowledgePlayer(gameName, playerName);
    //     } else {
    //         System.out.println("Game does not exist in AckService");
    //     }
    // }

    public List<String> toKey(String gameName, String type) {
        return new ArrayList<String>(Arrays.asList(gameName, type));
    }
}
