package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.service.GameService;

public class DayPhase extends Phase {
    @SuppressWarnings("unused")
    private GameService gameService;
    public DayPhase(Game game, GameService gameService) {
        super(game, PhaseType.DAY, true);
        this.gameService = gameService;
    }

    @Override
    public void execute() {
        System.out.println("\t\tDay Phase");
        System.out.println("\tIt is time to vote on the culprit.");
    }
    
}
