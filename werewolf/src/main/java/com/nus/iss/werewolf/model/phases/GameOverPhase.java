package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.Role;
import com.nus.iss.werewolf.service.GameService;

public class GameOverPhase extends Phase {
    private GameService gameService;
    public GameOverPhase(Game game, GameService gameService) {
        super(game, PhaseType.GAMEOVER, false);
        this.gameService = gameService;
    }

    @Override
    public void execute() {
        System.out.println("\t\tGameover!");
        System.out.printf("\t%s have won!\n", getWinner());
    }

    public Role getWinner() {
        if (gameService.isAllVillagersDead(game)) {
            return Role.WEREWOLF;
        }
        return Role.VILLAGER;
    }

}
