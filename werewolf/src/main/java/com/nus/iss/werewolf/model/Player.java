package com.nus.iss.werewolf.model;

import lombok.Data;

@Data
public class Player {
    private final String name;
    private Role role;
    private PlayerState state;

    public Player(String name) {
        this.name = name;
        this.state = PlayerState.ALIVE;
    }
}
