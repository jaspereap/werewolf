package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.service.GameService;

public class InitPhase extends Phase{
    private GameService gameService;

    public InitPhase(Game game, GameService gameService) {
        super(game, PhaseType.INIT, true);
        this.gameService = gameService;
    }

    @Override
    public void execute() {
        System.out.println("\tInit Phase");
        
        gameService.getAlivePlayers(game).forEach(p -> {System.out.println("\t" + p.getPlayerName() + " : " + p.getRole());});
        isActivated = false;
    }

}
