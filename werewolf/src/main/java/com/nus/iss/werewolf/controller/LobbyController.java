package com.nus.iss.werewolf.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.GameState;
import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.model.messages.CreateGameRequest;
import com.nus.iss.werewolf.model.messages.GameDTO;
import com.nus.iss.werewolf.model.messages.JoinGameRequest;
import com.nus.iss.werewolf.service.LobbyService;

import jakarta.json.Json;

@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = "*")
public class LobbyController {

    @Autowired
    private LobbyService lobbySvc;

    @PostMapping(path= "/create")
    public ResponseEntity<GameDTO> postCreateGame(@RequestBody CreateGameRequest request) {
        System.out.println("Post Create Game Controller");
        System.out.println("Game Name: " + request.getGameName());
        System.out.println("Player Name: " + request.getPlayerName());

        Player player = new Player(request.getPlayerName());
        Game game = new Game(request.getGameName(), new ArrayList<>(List.of(player)), GameState.CREATED);
        lobbySvc.createGame(game);
        
        return ResponseEntity.ok(new GameDTO(game));
    }

    @GetMapping(path= "/rooms")
    public ResponseEntity<List<GameDTO>> getGames() {
        System.out.println("Get Rooms Controller");
        List<GameDTO> games = lobbySvc.getGames();
        return ResponseEntity.ok(games);
    }

    @PostMapping(path = "/room/{gameName}")
    public ResponseEntity<GameDTO> postGetGame(@PathVariable String gameName, @RequestBody CreateGameRequest request) {
        System.out.println("Post Get Game Controller");
        System.out.println("request: " + request);
        System.out.println(this.lobbySvc.getGames());
        Optional<GameDTO> retrievedGame = this.lobbySvc.getGame(request.getGameName(), request.getPlayerName());
        if (retrievedGame.isEmpty()) {
            System.out.println("Game doesn't exist");
        }
        return ResponseEntity.ok(retrievedGame.get());
    }

    @PostMapping(path = "/join/{gameName}")
    public ResponseEntity<GameDTO> postJoinGame(@PathVariable String gameName, @RequestBody JoinGameRequest request) {
        System.out.println("Post Join Room");
        System.out.println("Room Name: " + gameName);
        System.out.println("Game Name: " + request.getGameName() + " Player Name: " + request.getPlayerName());
        Optional<Game> joinedGame = lobbySvc.joinGame(gameName, request.getPlayerName());
        if (joinedGame.isEmpty()) {
            System.out.println("JOIN GAME FAILED!");
        }
        System.out.println("\tJoined game: " + joinedGame.get());
        return ResponseEntity.ok(new GameDTO(joinedGame.get()));
        // return ResponseEntity.ok(new GameDTO(new Game()));
    }

    @PostMapping(path = "/leave/{gameName}")
    public ResponseEntity<String> postLeaveGame(@PathVariable String gameName, @RequestBody JoinGameRequest request) {
        System.out.println("Post Leave Game Controller");
        if (!this.lobbySvc.leaveGame(gameName, request.getPlayerName())) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("{'message':'FAIL'}");
        }
        return ResponseEntity.ok(Json.createObjectBuilder().add("message", "SUCCESS").build().toString());
    }
}
