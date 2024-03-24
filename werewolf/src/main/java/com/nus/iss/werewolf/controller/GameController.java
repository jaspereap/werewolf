package com.nus.iss.werewolf.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.messages.GameRequest;
import com.nus.iss.werewolf.repository.GameRepository;
import com.nus.iss.werewolf.service.AckService;
import com.nus.iss.werewolf.service.GameExecutor;

@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = "*")
public class GameController {
    @Autowired GameRepository gameRepo;
    @Autowired GameExecutor gameExecutor;
    @Autowired AckService ackService;

    @PostMapping(path = "/start/{gameId}")
    public ResponseEntity<String> postStartGame(@PathVariable String gameId, @RequestBody GameRequest request) {
        System.out.println("Post Start Game Controller");
        Optional<Game> startedGame = gameRepo.getGame(gameId);
        if (startedGame.isEmpty()) {
            return ResponseEntity.badRequest().body("{'message':'FAIL'}");
        }
        gameExecutor.startGame(startedGame.get());
        return ResponseEntity.ok("{\"message\":\"SUCCESS\"}");
    }

    // Acknowledges by GameName and MessageType
    @MessageMapping("/{gameId}/{playerName}/ack")
    public void playerAck(@DestinationVariable String gameId, 
        @DestinationVariable String playerName, 
        @Payload String body, 
        SimpMessageHeaderAccessor header) {
        System.out.printf("\tInbound Ack: \n\tgameId: %s \n\tplayerName: %s \n\tBody: %s\n", gameId, playerName, body);
        System.out.println("\tInbound Headers: " + header.getFirstNativeHeader("type"));
        String type = header.getFirstNativeHeader("type");

        if (ackService.hasGame(gameId, type)) {
            ackService.acknowledgePlayer(gameId, type);
        } else {
            System.out.println("Game does not exist in AckService");
        }
    }
}
