package com.nus.iss.werewolf.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.model.messages.GameDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LobbyService {

    // TODO: Mock Game db
    List<Game> games = new ArrayList<>();

    public void createGame(Game game) {
        games.add(game);
        log.debug("Game Created");
        System.out.println(games.getFirst());
    }

    public List<GameDTO> getGames() {
        log.debug("\tCurrent games: \n" + games);
        return games.stream().map(GameDTO::new).collect(Collectors.toList());
    }

    public Optional<GameDTO> getGame(String gameName, String playerName) {
        return games.stream()
            .filter(game -> game.getGameName().equals(gameName))
            .map(GameDTO::new)
            .findFirst();
    }

    public Optional<Game> joinGame(String gameName, String playerName) {
        return games.stream()
            .filter(x -> x.getGameName().equals(gameName))
            .peek(game -> addPlayer(game, playerName))
            .findFirst();
    }
    
    public boolean leaveGame(String gameName, String playerName) {
        for (Game game : games) {
            if (game.getGameName().equals(gameName)) {
                boolean exists = game.getPlayers().stream().anyMatch(player -> player.getPlayerName().equals(playerName));
                if (exists) {
                    game.getPlayers().removeIf(player -> player.getPlayerName().equals(playerName));
                    return true;
                } else {
                    System.out.println("Player " + playerName + " not found in game " + gameName);
                    return false;
                }
            }
        }
        System.out.println("Game " + gameName + " not found");
        return false;
    }
    



    private boolean addPlayer(Game game, String playerName) {
        // TODO: Retrieve player
        Player player = new Player(playerName);
        return game.getPlayers().add(player);
    }
    
}
