package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

public class GameOverPhase extends Phase {

    public GameOverPhase(Game game) {
        super(game, PhaseType.GAMEOVER);
    }

    @Override
    public void execute() {
        System.out.println("\tGameover!");
    }

}
