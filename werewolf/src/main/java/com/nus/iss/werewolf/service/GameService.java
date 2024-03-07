package com.nus.iss.werewolf.service;

import java.util.List;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.model.Role;
import com.nus.iss.werewolf.model.phases.Phase;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GameService {    
    @Autowired
    Executor executor;

    // public GameService(Executor executor) {
    //     this.executor = executor;
    // }

    public void startGame(Game game) {
        executor.execute(() -> {

            // Experiment
            int counter = 3;

            // Game initialization logic
            log.info("\n\tGame Started!");
            do {
                for (Phase p : game.getPhases()) {
                    log.info("\n\tCurrent Phase: " + p);
                    counter -= 1;
                    List<Player> aliveVillagers = game.getAlivePlayersByRole(Role.VILLAGER);
                    List<Player> aliveWerewolves = game.getAlivePlayersByRole(Role.WEREWOLF);
                    System.out.println("Alive villagers: " + aliveVillagers);
                    System.out.println("Alive Werewolves: " + aliveWerewolves);
                    System.out.println("Are villagers dead? " + game.isAllVillagersDead());
                    System.out.println("Are werewolves dead? " + game.isAllWerewolvesDead());
                    System.out.println("Is Game over? " + game.isGameOver());
 
                }
            } while (counter > 0); // GameOver condition
        });
    }

}
