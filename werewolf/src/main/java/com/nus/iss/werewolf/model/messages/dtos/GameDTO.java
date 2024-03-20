package com.nus.iss.werewolf.model.messages.dtos;

import java.util.List;

import lombok.Data;
import java.util.stream.Collectors;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.GameState;

@Data
public class GameDTO {
    private String gameName;
    private List<PlayerDTO> players;
    private GameState gameState;

    public GameDTO(Game game) {
        this.gameName = game.getGameName();
        this.gameState = game.getGameState();
        this.players = game.getPlayers().stream()
                               .map(PlayerDTO::new)
                               .collect(Collectors.toList());
        // this.players = game.getPlayers().stream()
        //                        .map(Player::getName) // Assuming Player class has a getName() method
        //                        .collect(Collectors.toList());
    }
}

