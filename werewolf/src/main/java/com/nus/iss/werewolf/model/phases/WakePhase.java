package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

public class WakePhase extends Phase{
    
    public WakePhase(Game game) {
        super(game, PhaseType.WAKE);
    }

    @Override
    public void execute() {
        System.out.println("\tWake phase");
    }

}
