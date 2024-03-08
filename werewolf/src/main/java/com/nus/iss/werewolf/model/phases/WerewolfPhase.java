package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

public class WerewolfPhase extends Phase {
    
    public WerewolfPhase(Game game) {
        super(game, PhaseType.WEREWOLF);
    }

    @Override
    public void execute() {
        System.out.println("\tWerewolf phase");
    }

}
