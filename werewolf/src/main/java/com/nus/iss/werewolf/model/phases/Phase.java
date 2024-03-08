package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

public abstract class Phase {
    public abstract void execute();
    protected Game game;
    protected PhaseType phaseType;
    protected boolean isActive;
    
    public Phase(Game game, PhaseType phaseType, boolean active) {
        this.game = game;
        this.phaseType = phaseType;
        this.isActive = active;
    }
    
    public Game getGame() {
        return this.game;
    };

    public PhaseType getPhase() {
        return this.phaseType;
    };

    public boolean isActive() {
        return isActive;
    }
    
}
