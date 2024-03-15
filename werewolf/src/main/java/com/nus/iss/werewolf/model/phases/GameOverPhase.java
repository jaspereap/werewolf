package com.nus.iss.werewolf.model.phases;

import org.springframework.beans.factory.annotation.Autowired;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.Role;
import com.nus.iss.werewolf.service.GameService;

public class GameOverPhase extends Phase {
    @Autowired
    GameService gameSvc;
    public GameOverPhase(Game game) {
        super(game, PhaseType.GAMEOVER, false);
    }

    @Override
    public void execute() {
        System.out.println("\t\tGameover!");
        System.out.printf("\t%s have won!\n", getWinner());
    }

    public Role getWinner() {
        if (gameSvc.isAllVillagersDead(game)) {
            return Role.WEREWOLF;
        }
        return Role.VILLAGER;
    }

}
