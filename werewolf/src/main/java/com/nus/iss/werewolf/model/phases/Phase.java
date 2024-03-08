package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

public abstract class Phase {
    public abstract void execute();
    protected Game game;
    protected PhaseType phaseType;
    
    public Phase(Game game, PhaseType phaseType) {
        this.game = game;
        this.phaseType = phaseType;
    }
    
    public Game getGame() {
        return this.game;
    };
    public PhaseType getPhase() {
        return this.phaseType;
    };
    
}
