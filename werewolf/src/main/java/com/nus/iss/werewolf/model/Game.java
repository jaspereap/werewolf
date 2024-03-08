package com.nus.iss.werewolf.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.nus.iss.werewolf.model.phases.Phase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Game {
    String name;
    private List<Phase> phases;
    private ArrayList<Player> players;
    private GameState gameState;

    public Game(String name, ArrayList<Player> players, GameState gameState) {
        this.name = name;
        this.players = players;
        this.gameState = gameState;
    }

    public List<Player> getAlivePlayers() {
        return players.stream()
                      .filter(player -> player.getState() == PlayerState.ALIVE)
                      .collect(Collectors.toList());
    }

    public List<Player> getAlivePlayersByRole(Role role) {
        return getAlivePlayers().stream()
                                .filter(player -> player.getRole() == role)
                                .collect(Collectors.toList());
    }

    public boolean isAllVillagersDead() {
        if (getAlivePlayersByRole(Role.VILLAGER).size() == 0) {
            return true;
        }
        return false;
    }

    public boolean isAllWerewolvesDead() {
        if (getAlivePlayersByRole(Role.WEREWOLF).size() == 0) {
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return isAllVillagersDead() | isAllWerewolvesDead();
    }
}
