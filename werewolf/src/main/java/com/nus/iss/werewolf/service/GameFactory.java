package com.nus.iss.werewolf.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.GameState;
import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.model.phases.DayPhase;
import com.nus.iss.werewolf.model.phases.InitPhase;
import com.nus.iss.werewolf.model.phases.NightPhase;
import com.nus.iss.werewolf.model.phases.Phase;

@Service
public class GameFactory {
    // Initialise game with one player
    public static Game initGame(String gameName, String playerName) {
        List<Phase> phases = initPhases();
        ArrayList<Player> players = new ArrayList<>(List.of(new Player(playerName)));
        Game game = new Game(gameName, phases, players, GameState.CREATED);
        return game;
    }
    // Initialise phases in a game
    public static List<Phase> initPhases() {
        List<Phase> phases = List.of(
            new InitPhase(),
            new NightPhase(),
            new DayPhase()
        );
        return phases;
    }
}
