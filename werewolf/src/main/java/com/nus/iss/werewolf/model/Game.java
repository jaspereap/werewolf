package com.nus.iss.werewolf.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.nus.iss.werewolf.model.phases.Phase;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Game {
    String gameName;
    private String gameId;
    private List<Phase> phases;
    private ArrayList<Player> players;
    private GameState gameState;

    public Game(String name, ArrayList<Player> players, GameState gameState) {
        this.gameName = name;
        this.players = players;
        this.gameState = gameState;
        this.gameId = UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }

    public JsonObject toJson() {
        JsonArrayBuilder playersArrayBuilder = Json.createArrayBuilder();
        for (Player player : players) {
            playersArrayBuilder.add(player.toJson());
        }
        JsonArrayBuilder phasesArrayBuilder = Json.createArrayBuilder();
        for (Phase phase : phases) {
            phasesArrayBuilder.add(phase.getPhase().toString());
        }
        return Json.createObjectBuilder()
            .add("name", gameName)
            .add("phases", phasesArrayBuilder)
            .add("players", playersArrayBuilder)
            .add("gameState", gameState.toString())
            .build();
    }
}
