package com.nus.iss.werewolf.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.Player;

@Service
public class LobbyService {
    public void addPlayer(Player player, Game game) {
        game.getPlayers().add(player);
    }
    public void addPlayers(List<Player> players, Game game) {
        players.forEach(player-> {
            game.getPlayers().add(player);
        });
    }
}
