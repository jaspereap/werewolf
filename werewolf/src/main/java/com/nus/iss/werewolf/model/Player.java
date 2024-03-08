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

    public void killPlayer() {
        this.state = PlayerState.DEAD;
        System.out.println("\t" + name + " has been killed!" + " (" + role + ")");
    }
}
