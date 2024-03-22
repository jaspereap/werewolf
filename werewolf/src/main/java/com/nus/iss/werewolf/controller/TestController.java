package com.nus.iss.werewolf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nus.iss.werewolf.service.GameService;
import com.nus.iss.werewolf.service.LobbyService;
import com.nus.iss.werewolf.service.MessageService;
import com.nus.iss.werewolf.service.MessageType;
import com.nus.iss.werewolf.service.RoleService;

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


    @GetMapping(path = "/current_games")
    public void getCurrentGames() {
        System.out.println("\nCurrent Games:\n" + lobbySvc.showGames());
    }

    @MessageMapping("/{gameName}/ack")
    public void acknowledge(@DestinationVariable String gameName, @Payload String body, SimpMessageHeaderAccessor header){
        System.out.printf("\tInbound: \n\tPath: %s \n\tBody: %s\n", gameName, body);
        System.out.println("\tInbound Headers: " + header.getFirstNativeHeader("type"));
    }

    @GetMapping(path = "/publishtoplayer/{gameName}/{playerName}")
    public void pubToPlayer(@PathVariable String gameName, @PathVariable String playerName) {
        msgSvc.publishToPlayer(gameName, playerName, "Test pub to player", MessageType.ACK);
    }
    @GetMapping(path = "/publishtogame/{gameName}")
    public void pubToGame(@PathVariable String gameName) {
        msgSvc.publishToGame(gameName, "Test pub to game", MessageType.ACK);
    }
}
