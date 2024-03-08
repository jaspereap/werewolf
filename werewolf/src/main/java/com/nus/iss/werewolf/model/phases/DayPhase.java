package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

public class DayPhase extends Phase {
    
    public DayPhase(Game game) {
        super(game, PhaseType.DAY);
    }

    @Override
    public void execute() {
        System.out.println("\tDay Phase");
    }
    
}
