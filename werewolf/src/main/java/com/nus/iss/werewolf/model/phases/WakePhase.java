package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

public class WakePhase extends Phase{
    
    public WakePhase(Game game) {
        super(game, PhaseType.WAKE, true);
    }

    @Override
    public void execute() {
        System.out.println("\t\tWake phase");
        System.out.println("\tThe sun rises, and the Village awakens.");
        System.out.println("\tThe Werewolves have killed ___.");
    }

}
