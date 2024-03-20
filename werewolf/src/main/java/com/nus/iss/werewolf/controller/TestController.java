package com.nus.iss.werewolf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nus.iss.werewolf.service.GameService;
import com.nus.iss.werewolf.service.LobbyService;
import com.nus.iss.werewolf.service.MessageService;
import com.nus.iss.werewolf.service.MessageType;
import com.nus.iss.werewolf.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
public class TestController {
    @Autowired
    MessageService msgSvc;
	@Autowired
	GameService gameSvc;
	@Autowired
	RoleService roleSvc;
	@Autowired
	LobbyService lobbySvc;

    @GetMapping(path = "/ack")
    public void getAck(@RequestParam String gameName) {
        // Test pushing game data to client
        msgSvc.publishToTopic(gameName, "acknowledged", MessageType.ACK);
    }

	@GetMapping(path = "/player_joined")
    public void getPlayerJoined(@RequestParam String gameName) {
        // Test pushing game data to client
        msgSvc.publishToTopic(gameName, "player joined", MessageType.PLAYER_JOINED);
    }

    @GetMapping(path = "/current_games")
    public void getCurrentGames() {
        System.out.println("\nCurrent Games:\n" + lobbySvc.getGames());
    }

    @MessageMapping("/{gameName}/ack")
    public void acknowledge(@DestinationVariable String gameName, @Payload String body, SimpMessageHeaderAccessor header){
        System.out.printf("\tInbound: \n\tPath: %s \n\tBody: %s\n", gameName, body);
        System.out.println("\tInbound Headers: " + header.getFirstNativeHeader("type"));
    }
}
