package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

public class WerewolfPhase extends Phase {
    
    public WerewolfPhase(Game game) {
        super(game, PhaseType.WEREWOLF, true);
    }

    @Override
    public void execute() {
        System.out.println("\t\tWerewolf phase");
        System.out.println("\tThe Werewolves awaken and kill.");
    }

}
