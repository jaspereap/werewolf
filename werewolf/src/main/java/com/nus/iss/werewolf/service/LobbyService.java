package com.nus.iss.werewolf.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.Player;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LobbyService {

    List<Game> games = new ArrayList<>();


    public void addPlayer(Player player, Game game) {
        game.getPlayers().add(player);
    }
    public void addPlayers(List<Player> players, Game game) {
        players.forEach(player-> {
            game.getPlayers().add(player);
        });
    }

    public void createGame(Game game) {
        games.add(game);
        log.debug("Game Created");
        System.out.println(games.getFirst());
    }

    public List<Game> getGames() {
        log.debug("Current games: " + games);
        return games;
    }
}
