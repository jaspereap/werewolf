package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

public class DayPhase extends Phase {
    
    public DayPhase(Game game) {
        super(game, PhaseType.DAY, true);
    }

    @Override
    public void execute() {
        System.out.println("\t\tDay Phase");
        System.out.println("\tIt is time to vote on the culprit.");
    }
    
}
