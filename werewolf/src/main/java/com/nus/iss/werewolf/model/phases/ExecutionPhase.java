package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

public class ExecutionPhase extends Phase {
        
    public ExecutionPhase(Game game) {
        super(game, PhaseType.EXECUTION);
    }

    @Override
    public void execute() {
        System.out.println("\tExecution phase");
    }
}
