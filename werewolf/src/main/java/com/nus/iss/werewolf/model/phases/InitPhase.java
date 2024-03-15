package com.nus.iss.werewolf.model.phases;

import org.springframework.beans.factory.annotation.Autowired;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.service.GameService;

public class InitPhase extends Phase{
    @Autowired
    GameService gameSvc;

    public InitPhase(Game game) {
        super(game, PhaseType.INIT, true);
    }

    @Override
    public void execute() {
        System.out.println("\tInit Phase");
        
        gameSvc.getAlivePlayers(game).forEach(p -> {System.out.println("\t" + p.getPlayerName() + " : " + p.getRole());});
        isActivated = false;
    }

}
