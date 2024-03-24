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
public class GameExecutor {
    @Autowired @Qualifier("executor") private Executor executor;
    @Autowired MessageService msgService;
    @Autowired GameService gameService;
    @Autowired RoleService roleService;
    @Autowired AckService ackService;

   public void startGame(Game game) {
        executor.execute(() -> {
            // Game initialization logic
            log.info("\n\tGame Started!");
            try {
                // Broadcast Init_game
                // msgService.publishToGame(game.getGameName(), "", MessageType.INIT_GAME);
                msgService.publishToGameWithAck(game.getGameId(), "", MessageType.INIT_GAME, game.getPlayers().size());
                Thread.sleep(1000);
                // Broadcast Start_game
                msgService.publishToGameWithAck(game.getGameId(), "", MessageType.START_GAME, game.getPlayers().size());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Assign roles
            roleService.assignRoles(game);

            // Proceed game when all acked
            do {
                for (Phase p : game.getPhases()) {
                    if (p.isActivated()) {
                        p.execute();
                        // Mocking ------
                        if (p.getPhase() == PhaseType.EXECUTION) {
                            // game.getAlivePlayers().getFirst().killPlayer()
                        } else if (p.getPhase() == PhaseType.WEREWOLF) {
                            gameService.getAlivePlayersByRole(Role.VILLAGER, game).getFirst().killPlayer();
                        }
                        // -------
                        List<Player> aliveVillagers = gameService.getAlivePlayersByRole(Role.VILLAGER, game);
                        List<Player> aliveWerewolves = gameService.getAlivePlayersByRole(Role.WEREWOLF, game);
                        System.out.println("\t\tAlive villagers: " + aliveVillagers + "\n");
                        System.out.println("\t\tAlive Werewolves: " + aliveWerewolves + "\n");
                        System.out.println("Is Game over? " + gameService.isGameOver(game));
                    }
                    if (gameService.isGameOver(game)) {
                        log.info("Game ended!");
                        game.getPhases().get(PhaseType.GAMEOVER.ordinal()).execute();
                        break;
                    }
                }
                
            } while (!gameService.isGameOver(game));
        });
    }
}
