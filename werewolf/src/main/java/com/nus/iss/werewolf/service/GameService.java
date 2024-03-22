package com.nus.iss.werewolf.service;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.model.PlayerState;
import com.nus.iss.werewolf.model.Role;
import com.nus.iss.werewolf.repository.GameRepository;

@Service
public class GameService {    
    @Autowired @Qualifier("executor") private Executor executor;
    @Autowired MessageService msgService;
    // TODO: WIP
    @Autowired private GameRepository gameRepo;

    // public void 

    public List<Player> getAlivePlayers(Game game) {
        return game.getPlayers().stream()
                      .filter(player -> player.getPlayerState() == PlayerState.ALIVE)
                      .collect(Collectors.toList());
    }
    public List<Player> getAlivePlayersByRole(Role role, Game game) {
        return getAlivePlayers(game).stream()
                                .filter(player -> player.getRole() == role)
                                .collect(Collectors.toList());
    }
    public boolean isAllVillagersDead(Game game) {
        if (getAlivePlayersByRole(Role.VILLAGER, game).size() == 0) {
            return true;
        }
        return false;
    }
    public boolean isAllWerewolvesDead(Game game) {
        if (getAlivePlayersByRole(Role.WEREWOLF, game).size() == 0) {
            return true;
        }
        return false;
    }
    public boolean isGameOver(Game game) {
        return isAllVillagersDead(game) | isAllWerewolvesDead(game);
    }

}
