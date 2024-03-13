package com.nus.iss.werewolf.service;

import java.util.List;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.Player;
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
                            game.getAlivePlayersByRole(Role.VILLAGER).getFirst().killPlayer();
                        }
                        // -------
                        List<Player> aliveVillagers = game.getAlivePlayersByRole(Role.VILLAGER);
                        List<Player> aliveWerewolves = game.getAlivePlayersByRole(Role.WEREWOLF);
                        System.out.println("\t\tAlive villagers: " + aliveVillagers + "\n");
                        System.out.println("\t\tAlive Werewolves: " + aliveWerewolves + "\n");
                        System.out.println("Is Game over? " + game.isGameOver());
                    }
                    if (game.isGameOver()) {
                        log.info("Game ended!");
                        game.getPhases().get(PhaseType.GAMEOVER.ordinal()).execute();
                        break;
                    }
                }
                
            } while (!game.isGameOver());
        });
    }

}
