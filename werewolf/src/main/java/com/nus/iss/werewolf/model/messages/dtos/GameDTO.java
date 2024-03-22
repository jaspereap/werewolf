package com.nus.iss.werewolf.model.messages.dtos;

import java.util.List;

import lombok.Data;
import java.util.stream.Collectors;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.GameState;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Data
public class GameDTO {
    private String gameName;
    private List<PlayerDTO> players;
    private GameState gameState;

    public GameDTO(Game game) {
        this.gameName = game.getGameName();
        this.gameState = game.getGameState();
        this.players = game.getPlayers().stream()
                               .map(PlayerDTO::new)
                               .collect(Collectors.toList());
        // this.players = game.getPlayers().stream()
        //                        .map(Player::getName) // Assuming Player class has a getName() method
        //                        .collect(Collectors.toList());
    }
    public JsonObject toJson() {
        JsonArrayBuilder playersArrayBuilder = Json.createArrayBuilder();
        for (PlayerDTO player : players) {
            playersArrayBuilder.add(player.toJson());
        }
        return Json.createObjectBuilder()
            .add("gameName", gameName)
            .add("players", playersArrayBuilder)
            .add("gameState", gameState.toString())
            .build();
    }
}

