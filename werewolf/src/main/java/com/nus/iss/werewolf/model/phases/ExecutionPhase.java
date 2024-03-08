package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

public class ExecutionPhase extends Phase {
        
    public ExecutionPhase(Game game) {
        super(game, PhaseType.EXECUTION, true);
    }

    @Override
    public void execute() {
        System.out.println("\t\tExecution phase");
        System.out.println("\t____ has been accused as a Werewolf.");
        System.out.println("\tAccused werewolf, you have 30 seconds to defend yourself,");
        System.out.println("\tthen the Village will vote whether to kill you.");
    }
}
