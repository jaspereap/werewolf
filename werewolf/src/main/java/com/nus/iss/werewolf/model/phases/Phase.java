package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Phase {
    public abstract void execute();
    protected Game game;
    protected PhaseType phaseType;
    protected boolean isActivated;
    
    
    public Phase(Game game, PhaseType phaseType, boolean activated) {
        this.game = game;
        this.phaseType = phaseType;
        this.isActivated = activated;
    }
    
    public Game getGame() {
        return this.game;
    };

    public PhaseType getPhase() {
        return this.phaseType;
    };

    public boolean isActivated() {
        return isActivated;
    }

    public String toString() {
        return phaseType.toString();
    }
}
