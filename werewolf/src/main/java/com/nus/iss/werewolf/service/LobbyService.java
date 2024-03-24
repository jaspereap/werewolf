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

    public Optional<Game> joinGame(String gameId, Player player) {
        return gameRepo.getGames().stream()
            .filter(x -> x.getGameId().equals(gameId))
            .peek(game -> addPlayer(game, player))
            .findFirst();
    }
    // public Optional<Game> joinGame(String gameId, String playerId) {
    //     return gameRepo.getGames().stream()
    //         .filter(x -> x.getGameId().equals(gameId))
    //         .peek(game -> addPlayer(game, playerId))
    //         .findFirst();
    // }
    
    public boolean leaveGame(String gameId, Player player) {
        return gameRepo.removePlayerFromGame(gameId, player.getPlayerId());
    }

    private boolean addPlayer(Game game, Player player) {
        // TODO: Retrieve player
        // Player player = new Player(playerName);
        return game.getPlayers().add(player);
    }
    
}
