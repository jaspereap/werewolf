package com.nus.iss.werewolf.model.phases;

import com.nus.iss.werewolf.model.Game;

public class InitPhase extends Phase{

    public InitPhase(Game game) {
        super(game, PhaseType.INIT, true);
    }

    @Override
    public void execute() {
        System.out.println("\tInit Phase");
        game.getAlivePlayers().forEach(p -> {System.out.println("\t" + p.getName() + " : " + p.getRole());});
        isActivated = false;
    }

}
