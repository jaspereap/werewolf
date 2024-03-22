package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.service.GameService;

public class NightPhase extends Phase {
    @SuppressWarnings("unused")
    private GameService gameService;
    public NightPhase(Game game, GameService gameService) {
        super(game, PhaseType.NIGHT, true);
        this.gameService = gameService;
    }

    @Override
    public void execute() {
        System.out.println("\t\tNight Phase");
        System.out.println("\tThe Night is upon us, and the Village goes to sleep.");
    }

}
