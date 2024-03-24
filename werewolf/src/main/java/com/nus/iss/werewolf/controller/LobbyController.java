package com.nus.iss.werewolf.controller;

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
import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.model.messages.GameRequest;
import com.nus.iss.werewolf.model.messages.dtos.GameDTO;
import com.nus.iss.werewolf.model.messages.dtos.PlayerDTO;
import com.nus.iss.werewolf.service.GameFactory;
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

    @Autowired
    private GameFactory gameFactory;

    @PostMapping(path= "/create")
    public ResponseEntity<GameDTO> postCreateGame(@RequestBody GameRequest request) {
        System.out.println("Post Create Game Controller");
        System.out.println("\tCreated Game Name: " + request.getGameName());
        System.out.println("\tCreator Player Name: " + request.getPlayerName());

        Game game = gameFactory.initGame(request.getGameName(), request.getPlayerName());
        lobbySvc.createGame(game);
        return ResponseEntity.ok(new GameDTO(game));
    }

    @GetMapping(path= "/rooms")
    public ResponseEntity<List<GameDTO>> getGames() {
        System.out.println("Get Rooms Controller");
        List<GameDTO> games = lobbySvc.getGames();
        return ResponseEntity.ok(games);
    }

    @PostMapping(path = "/room/{gameId}")
    public ResponseEntity<String> postGameDetail(@PathVariable String gameId, @RequestBody GameRequest request) {
        System.out.println("Post Game Detail Controller");
        Optional<GameDTO> retrievedGame = this.lobbySvc.getGame(request.getGameId(), request.getPlayerName());
        if (retrievedGame.isEmpty()) {
            System.out.println("Game doesn't exist");
            return ResponseEntity.badRequest().body("{'message':'FAIL'}");
        }
        return ResponseEntity.ok(retrievedGame.get().toJson().toString());
    }

    @PostMapping(path = "/join/{gameId}")
    public ResponseEntity<String> postJoinGame(@PathVariable String gameId, @RequestBody GameRequest request) {
        System.out.println("Post Join Room");
        System.out.println("\tGame Id: " + request.getGameId() + " Player Name: " + request.getPlayerName());
        // TODO: Get player object
        Player player = new Player(request.getPlayerName());
        PlayerDTO playerDTO = new PlayerDTO(player);
        Optional<Game> joinedGame = lobbySvc.joinGame(gameId, request.getPlayerName());
        if (joinedGame.isEmpty()) {
            System.out.println("\tJOIN GAME FAILED!");
            return ResponseEntity.badRequest().body("{'message':'FAIL'}");
        }
        // Broadcast room join
        System.out.println(playerDTO.toString());
        msgSvc.publishToGame(gameId, playerDTO.toJson().toString(), MessageType.PLAYER_JOINED);
        return ResponseEntity.ok(new GameDTO(joinedGame.get()).toJson().toString());
    }

    @PostMapping(path = "/leave/{gameId}")
    public ResponseEntity<String> postLeaveGame(@PathVariable String gameId, @RequestBody GameRequest request) {
        System.out.println("Post Leave Game Controller");
        // TODO: Get player object
        Player player = new Player(request.getPlayerName());
        PlayerDTO playerDTO = new PlayerDTO(player);
        if (!this.lobbySvc.leaveGame(gameId, request.getPlayerName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'message':'FAIL'}");
        }
        // Broadcast room leave
        msgSvc.publishToGame(gameId, playerDTO.toJson().toString(), MessageType.PLAYER_LEFT);
        return ResponseEntity.ok("{\"message\":\"SUCCESS\"}");
    }
}
