package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

public class NightPhase extends Phase {

    public NightPhase(Game game) {
        super(game, PhaseType.NIGHT);
    }

    @Override
    public void execute() {
        System.out.println("\tNight Phase");
    }

}
