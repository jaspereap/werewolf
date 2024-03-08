package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.Role;

public class GameOverPhase extends Phase {

    public GameOverPhase(Game game) {
        super(game, PhaseType.GAMEOVER, false);
    }

    @Override
    public void execute() {
        System.out.println("\t\tGameover!");
        System.out.printf("\t%s have won!\n", getWinner());
    }

    public Role getWinner() {
        if (game.isAllVillagersDead()) {
            return Role.WEREWOLF;
        }
        return Role.VILLAGER;
    }

}
