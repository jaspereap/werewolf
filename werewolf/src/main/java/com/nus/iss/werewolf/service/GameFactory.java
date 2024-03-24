package com.nus.iss.werewolf.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    GameService gameService;

    // Initialise game with one player (room creator)
    public Game initGame(String gameName, Player player) {
        // Mock Players
        ArrayList<Player> players = new ArrayList<>(List.of());

        players.add(player);
        
        Game game = new Game(gameName, players, GameState.CREATED);
        List<Phase> phases = initPhases(game);
        game.setPhases(phases);
        return game;
    }
    // TODO: add new phases here
    private List<Phase> initPhases(Game game) {
        List<Phase> phases = List.of(
            new InitPhase(game, gameService),
            new NightPhase(game, gameService),
            new WerewolfPhase(game, gameService),
            new WakePhase(game, gameService),
            new DayPhase(game, gameService),
            new ExecutionPhase(game, gameService),
            new GameOverPhase(game, gameService)
        );
        return phases;
    }
}
