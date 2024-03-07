package com.nus.iss.werewolf.service;



import java.util.ArrayList;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.model.Role;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleService {
    Random random = new Random();

    public void assignRoles(Game game) {
        log.info("\n\tAssigning roles to players...");
        ArrayList<Player> players = game.getPlayers();
        int playerCount = players.size();
        int werewolfCount = 2;

        // Assign werewolves first
        for (int i = 0; i < werewolfCount; i++) {
            players.get(random.nextInt(0, playerCount)).setRole(Role.WEREWOLF);
        }
        // Assign remaining as villagers
        for (Player player : players) {
            if(player.getRole() == null) {
                player.setRole(Role.VILLAGER);
            }
        }
    }
}
