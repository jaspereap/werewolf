package com.nus.iss.werewolf.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.GameState;
import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.model.phases.DayPhase;
import com.nus.iss.werewolf.model.phases.ExecutionPhase;
import com.nus.iss.werewolf.model.phases.GameOverPhase;
import com.nus.iss.werewolf.model.phases.InitPhase;
import com.nus.iss.werewolf.model.phases.NightPhase;
import com.nus.iss.werewolf.model.phases.Phase;
import com.nus.iss.werewolf.model.phases.WakePhase;
import com.nus.iss.werewolf.model.phases.WerewolfPhase;

@Service
public class GameFactory {
    // Initialise game with one player (room creator)
    public static Game initGame(String gameName, String playerName) {
        ArrayList<Player> players = new ArrayList<>(List.of(new Player(playerName)));
        Game game = new Game(gameName, players, GameState.CREATED);
        List<Phase> phases = initPhases(game);
        game.setPhases(phases);
        return game;
    }
    // TODO: add new phases here
    private static List<Phase> initPhases(Game game) {
        List<Phase> phases = List.of(
            new InitPhase(game),
            new NightPhase(game),
            new WerewolfPhase(game),
            new WakePhase(game),
            new DayPhase(game),
            new ExecutionPhase(game),
            new GameOverPhase(game)
        );
        return phases;
    }
}
