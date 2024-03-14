package com.nus.iss.werewolf.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.GameState;
import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.model.messages.CreateGameRequest;
import com.nus.iss.werewolf.service.LobbyService;

import jakarta.json.Json;

@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = "*")
public class LobbyController {

    @Autowired
    private LobbyService lobbySvc;

    @PostMapping(path= "/create")
    public String postCreateGame(@RequestBody CreateGameRequest request) {
        System.out.println("Post Create Game Controller");
        System.out.println("Game Name: " + request.getGameName());
        System.out.println("Player Name: " + request.getPlayerName());

        Player player = new Player(request.getPlayerName());
        Game game = new Game(request.getGameName(), new ArrayList<>(List.of(player)), GameState.CREATED);
        lobbySvc.createGame(game);
        return Json.createObjectBuilder().add("message", "SUCCESS").build().toString();
    }

    @GetMapping(path= "/rooms")
    public List<Game> getRooms() {
        System.out.println("Get Rooms Controller");
        List<Game> games = lobbySvc.getGames();
        return games;
    }
}
