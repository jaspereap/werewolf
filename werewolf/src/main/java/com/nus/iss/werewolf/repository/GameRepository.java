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

    public Optional<Game> getGame(String gameId) {
        return games.stream()
            .filter(game -> game.getGameId().equals(gameId))
            .findFirst();
    }
    public boolean removePlayerFromGame(String gameId, String playerId) {
        for (Game game : getGames()) {
            if (game.getGameId().equals(gameId)) {
                game.getPlayers().removeIf(player -> player.getPlayerId().equals(playerId));
                return true;
            }
        }
        return false;
    }
}

