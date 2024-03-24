package com.nus.iss.werewolf.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.model.messages.dtos.GameDTO;
import com.nus.iss.werewolf.repository.GameRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LobbyService {

    @Autowired GameRepository gameRepo;
    
    public List<Game> showGames() {
        return gameRepo.getGames();
    }

    public void createGame(Game game) {
        gameRepo.addGame(game);
        log.debug("Game Created");
        System.out.println(gameRepo.getGames().getFirst());
    }

    public List<GameDTO> getGames() {
        log.debug("\tCurrent games: \n" + gameRepo.getGames());
        return gameRepo.getGames().stream().map(GameDTO::new).collect(Collectors.toList());
    }

    public Optional<GameDTO> getGame(String gameId, String playerName) {
        return gameRepo.getGames().stream()
            .filter(game -> game.getGameId().equals(gameId))
            .map(GameDTO::new)
            .findFirst();
    }

    public Optional<Game> joinGame(String gameId, String playerName) {
        return gameRepo.getGames().stream()
            .filter(x -> x.getGameId().equals(gameId))
            .peek(game -> addPlayer(game, playerName))
            .findFirst();
    }
    
    public boolean leaveGame(String gameId, String playerName) {
        for (Game game : gameRepo.getGames()) {
            if (game.getGameId().equals(gameId)) {
                boolean exists = game.getPlayers().stream().anyMatch(player -> player.getPlayerName().equals(playerName));
                if (exists) {
                    game.getPlayers().removeIf(player -> player.getPlayerName().equals(playerName));
                    return true;
                } else {
                    System.out.println("Player " + playerName + " not found in game " + gameId);
                    return false;
                }
            }
        }
        System.out.println("Game " + gameId + " not found");
        return false;
    }

    private boolean addPlayer(Game game, String playerName) {
        // TODO: Retrieve player
        Player player = new Player(playerName);
        return game.getPlayers().add(player);
    }
    
}
