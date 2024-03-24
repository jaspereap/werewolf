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

    public void initAck(String gameId, MessageType type, int responseCount) {
        System.out.printf("initAck -> inject %s, %s into gameAckLatches\n", gameId, type.toString());
        List<String> key = toKey(gameId, type.toString());
        if (gameAckLatches.containsKey(key)) {
            clearAcknowledgment(gameId, type);
        }
        gameAckLatches.put(key, new CountDownLatch(responseCount));
        System.out.println(gameAckLatches);
    }

    public boolean hasGame(String gameId, String type) {
        return gameAckLatches.containsKey(toKey(gameId, type));
    }

    public boolean waitForAcknowledgments(String gameId, MessageType type, long timeout, TimeUnit unit) {
        System.out.println("Waiting for acks.........");
        CountDownLatch latch = gameAckLatches.get(toKey(gameId, type.toString()));
        System.out.println(gameAckLatches);

        try {
            // Returns true if the count reached zero and false if the waiting time elapsed before the count reached zero
            return latch.await(timeout, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            return false;
        }
    }

    public void acknowledgePlayer(String gameId, String type) {
        CountDownLatch latch = gameAckLatches.get(toKey(gameId, type));
        if (latch != null) {
            latch.countDown();
            // Optionally log the acknowledgment or notify other parts of the system
            System.out.println("");
            System.out.println(gameAckLatches);
        }
    }

    public void clearAcknowledgment(String gameId, MessageType type) {
        gameAckLatches.remove(toKey(gameId, type.toString()));
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

    public List<String> toKey(String gameId, String type) {
        return new ArrayList<String>(Arrays.asList(gameId, type));
    }
}
