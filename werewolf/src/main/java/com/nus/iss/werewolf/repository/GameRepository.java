package com.nus.iss.werewolf.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.nus.iss.werewolf.model.Game;

@Repository
public class GameRepository {
    List<Game> games = new ArrayList<>();

    public List<Game> getGames() {
        return games;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }

    public Optional<Game> getGame(String gameName) {
        return games.stream()
            .filter(game -> game.getGameName().equals(gameName))
            .findFirst();
    }
}

