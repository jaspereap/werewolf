package com.nus.iss.werewolf.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.GameState;
import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.model.messages.CreateGameRequest;
import com.nus.iss.werewolf.model.messages.JoinGameRequest;
import com.nus.iss.werewolf.model.messages.dtos.GameDTO;
import com.nus.iss.werewolf.model.messages.dtos.PlayerDTO;
import com.nus.iss.werewolf.service.LobbyService;
import com.nus.iss.werewolf.service.MessageService;
import com.nus.iss.werewolf.service.MessageType;

@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = "*")
public class LobbyController {

    @Autowired
    private LobbyService lobbySvc;

    @Autowired
    private MessageService msgSvc;

    @PostMapping(path= "/create")
    public ResponseEntity<GameDTO> postCreateGame(@RequestBody CreateGameRequest request) {
        System.out.println("Post Create Game Controller");
        System.out.println("\tCreated Game Name: " + request.getGameName());
        System.out.println("\tCreator Player Name: " + request.getPlayerName());

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
    public ResponseEntity<GameDTO> postGameDetail(@PathVariable String gameName, @RequestBody CreateGameRequest request) {
        System.out.println("Post Game Detail Controller");
        Optional<GameDTO> retrievedGame = this.lobbySvc.getGame(request.getGameName(), request.getPlayerName());
        if (retrievedGame.isEmpty()) {
            System.out.println("Game doesn't exist");
        }
        return ResponseEntity.ok(retrievedGame.get());
    }

    @PostMapping(path = "/join/{gameName}")
    public ResponseEntity<GameDTO> postJoinGame(@PathVariable String gameName, @RequestBody JoinGameRequest request) {
        System.out.println("Post Join Room");
        System.out.println("\tGame Name: " + request.getGameName() + " Player Name: " + request.getPlayerName());
        // TODO: Get player object
        Player player = new Player(request.getPlayerName());
        PlayerDTO playerDTO = new PlayerDTO(player);
        Optional<Game> joinedGame = lobbySvc.joinGame(gameName, request.getPlayerName());
        if (joinedGame.isEmpty()) {
            System.out.println("\tJOIN GAME FAILED!");
        }
        // Broadcast room join
        System.out.println(playerDTO.toString());
        msgSvc.publishToTopic(gameName, playerDTO.toJson().toString(), MessageType.PLAYER_JOINED);
        return ResponseEntity.ok(new GameDTO(joinedGame.get()));
    }

    @PostMapping(path = "/leave/{gameName}")
    public ResponseEntity<String> postLeaveGame(@PathVariable String gameName, @RequestBody JoinGameRequest request) {
        System.out.println("Post Leave Game Controller");
        // TODO: Get player object
        Player player = new Player(request.getPlayerName());
        PlayerDTO playerDTO = new PlayerDTO(player);
        if (!this.lobbySvc.leaveGame(gameName, request.getPlayerName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'message':'FAIL'}");
        }
        // Broadcast room leave
        msgSvc.publishToTopic(gameName, playerDTO.toJson().toString(), MessageType.PLAYER_LEFT);
        return ResponseEntity.ok("{\"message\":\"SUCCESS\"}");
    }
}
