package com.nus.iss.werewolf.model.messages.dtos;

import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.model.PlayerState;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.Data;

@Data
public class PlayerDTO {
    String playerName;
    String playerId;
    PlayerState playerState;

    public PlayerDTO(Player player) {
        this.playerName = player.getPlayerName();
        this.playerId = player.getPlayerId();
        this.playerState = player.getPlayerState();
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("playerName", playerName)
            .add("playerId", playerId)
            .add("playerState", playerState.toString())
            .build();
    }
}
