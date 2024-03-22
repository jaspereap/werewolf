package com.nus.iss.werewolf.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.Data;

@Data
public class Player {
    private final String playerName;
    private Role role;
    private PlayerState playerState;

    public Player(String name) {
        this.playerName = name;
        this.role = Role.VILLAGER;
        this.playerState = PlayerState.ALIVE;
    }

    public void killPlayer() {
        this.playerState = PlayerState.DEAD;
        System.out.println("\t" + playerName + " has been killed!" + " (" + role + ")");
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                    .add("playerName", playerName)
                    .add("role", role.toString())
                    .add("playerState", playerState.toString())
                    .build();
    }
}
