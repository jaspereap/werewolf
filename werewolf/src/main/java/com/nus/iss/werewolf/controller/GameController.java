package com.nus.iss.werewolf.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.service.GameFactory;
import com.nus.iss.werewolf.service.GameService;
import com.nus.iss.werewolf.service.LobbyService;
import com.nus.iss.werewolf.service.MessageContents;
import com.nus.iss.werewolf.service.MessageService;
import com.nus.iss.werewolf.service.MessageType;
import com.nus.iss.werewolf.service.RoleService;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
public class GameController {
    @Autowired
    MessageService msgSvc;
	@Autowired
	GameService gameSvc;
	@Autowired
	RoleService roleSvc;
	@Autowired
	LobbyService lobbySvc;

    @GetMapping(path = "/test")
    public void test() {
		// Create and initialise game
		Game game = GameFactory.initGame("testGame", "bob");

		// Add players
		ArrayList<Player> players = new ArrayList<>(List.of(
			new Player("Bobby"),
			new Player("John"),
			new Player("Jopie"),
			new Player("Jookoon"),
			new Player("Blop"),
			new Player("Poopie")
		));
		lobbySvc.addPlayers(players, game);

		// Assign Roles
		roleSvc.assignRoles(game);
        // String gameInitResponse = Json.createObjectBuilder().add("type", "GAMEINIT").add("game", game.toJson()).build().toString();
        JsonObject gameInitResponse = msgSvc.addType(MessageType.GAME_INIT, game.toJson());

        // Test pushing game data to client
        msgSvc.publishToGame("testGame", gameInitResponse.toString());
        System.out.printf("Published to game `%s`\n", "testGame");
    }
}
