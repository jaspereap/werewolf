package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.service.GameService;

public class WakePhase extends Phase{
    @SuppressWarnings("unused")
    private GameService gameService;
    public WakePhase(Game game, GameService gameService) {
        super(game, PhaseType.WAKE, true);
        this.gameService = gameService;
    }

    @Override
    public void execute() {
        System.out.println("\t\tWake phase");
        System.out.println("\tThe sun rises, and the Village awakens.");
        System.out.println("\tThe Werewolves have killed ___.");
    }

}
