package com.nus.iss.werewolf.service;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
@CrossOrigin(origins = "*")
public class AckService {
    private final Map<String, CountDownLatch> gameAckLatches = new ConcurrentHashMap<>();

    public void initAck(String gameName, int players) {
        System.out.printf("initAck -> inject %s into gameAckLatches\n", gameName);
        gameAckLatches.put(gameName, new CountDownLatch(players));
        System.out.println(gameAckLatches);
    }
    public boolean hasGame(String gameName) {
        return gameAckLatches.containsKey(gameName);
    }

    public boolean waitForAcknowledgments(String gameName, long timeout, TimeUnit unit) {
        System.out.println("Waiting for acks.........");
        CountDownLatch latch = gameAckLatches.get(gameName);
        System.out.println(gameAckLatches);

        try {
            // Returns true if the count reached zero and false if the waiting time elapsed before the count reached zero
            return latch.await(timeout, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            return false;
        }
    }

    public void acknowledgePlayer(String gameName, String playerName) {
        CountDownLatch latch = gameAckLatches.get(gameName);
        if (latch != null) {
            latch.countDown();
            // Optionally log the acknowledgment or notify other parts of the system
            System.out.println("\tAck From Player: " + playerName + "\n From game: " + gameName);
            System.out.println(gameAckLatches);
        }
    }

    public void clearAcknowledgment(String gameName) {
        gameAckLatches.remove(gameName);
    }
    
    @MessageMapping("/{gameName}/{playerName}/ack")
    public void playerAck(@DestinationVariable String gameName, 
        @DestinationVariable String playerName, 
        @Payload String body, 
        SimpMessageHeaderAccessor header) {
        System.out.printf("\tInbound Ack: \n\tgameName: %s \n\tplayerName: %s \n\tBody: %s\n", gameName, playerName, body);
        System.out.println("\tInbound Headers: " + header.getFirstNativeHeader("type"));
        if (hasGame(gameName)) {
            acknowledgePlayer(gameName, playerName);
        } else {
            System.out.println("Game does not exist in AckService");
        }
    }
}
