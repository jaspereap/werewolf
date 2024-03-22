package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.service.GameService;

public class WerewolfPhase extends Phase {
    @SuppressWarnings("unused")
    private GameService gameService;
    public WerewolfPhase(Game game, GameService gameService) {
        super(game, PhaseType.WEREWOLF, true);
        this.gameService = gameService;
    }

    @Override
    public void execute() {
        System.out.println("\t\tWerewolf phase");
        System.out.println("\tThe Werewolves awaken and kill.");
    }

}
