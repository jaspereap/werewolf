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
import com.nus.iss.werewolf.model.phases.Phase;
import com.nus.iss.werewolf.model.phases.PhaseType;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GameService {    
    @Autowired @Qualifier("executor")
    private Executor executor;

    // public GameService(Executor executor) {
    //     this.executor = executor;
    // }

    public void startGame(Game game) {
        executor.execute(() -> {
            // Game initialization logic
            log.info("\n\tGame Started!");
            do {
                for (Phase p : game.getPhases()) {
                    if (p.isActivated()) {
                        p.execute();
                        // Mocking ------
                        if (p.getPhase() == PhaseType.EXECUTION) {
                            // game.getAlivePlayers().getFirst().killPlayer()
                        } else if (p.getPhase() == PhaseType.WEREWOLF) {
                            getAlivePlayersByRole(Role.VILLAGER, game).getFirst().killPlayer();
                        }
                        // -------
                        List<Player> aliveVillagers = getAlivePlayersByRole(Role.VILLAGER, game);
                        List<Player> aliveWerewolves = getAlivePlayersByRole(Role.WEREWOLF, game);
                        System.out.println("\t\tAlive villagers: " + aliveVillagers + "\n");
                        System.out.println("\t\tAlive Werewolves: " + aliveWerewolves + "\n");
                        System.out.println("Is Game over? " + isGameOver(game));
                    }
                    if (isGameOver(game)) {
                        log.info("Game ended!");
                        game.getPhases().get(PhaseType.GAMEOVER.ordinal()).execute();
                        break;
                    }
                }
                
            } while (!isGameOver(game));
        });
    }

    public List<Player> getAlivePlayers(Game game) {
        return game.getPlayers().stream()
                      .filter(player -> player.getState() == PlayerState.ALIVE)
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
