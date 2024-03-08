package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

public class NightPhase extends Phase {

    public NightPhase(Game game) {
        super(game, PhaseType.NIGHT, true);
    }

    @Override
    public void execute() {
        System.out.println("\t\tNight Phase");
        System.out.println("\tThe Night is upon us, and the Village goes to sleep.");
    }

}
