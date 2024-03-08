package com.nus.iss.werewolf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nus.iss.werewolf.model.Game;
import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.model.PlayerState;
import com.nus.iss.werewolf.model.Role;
import com.nus.iss.werewolf.model.phases.DayPhase;
import com.nus.iss.werewolf.model.phases.NightPhase;
import com.nus.iss.werewolf.model.phases.Phase;
import com.nus.iss.werewolf.model.phases.PhaseType;
import com.nus.iss.werewolf.service.GameFactory;
import com.nus.iss.werewolf.service.GameService;
import com.nus.iss.werewolf.service.LobbyService;
import com.nus.iss.werewolf.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class WerewolfApplication implements CommandLineRunner {
	@Autowired
	GameService gameSvc;
	@Autowired
	RoleService roleSvc;
	@Autowired
	LobbyService lobbySvc;
	public static void main(String[] args) {
		SpringApplication.run(WerewolfApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Create and initialise game
		Game game = GameFactory.initGame("Test Game", "bob");

		// Add players
		ArrayList<Player> players = new ArrayList<>(List.of(
			new Player("Bobby"),
			new Player("John"),
			new Player("Jopie"),
			new Player("Jookoon"),
			new Player("Blop"),
			new Player("Poopie")
		));
		lobbySvc.addPlayers(players, game);

		// Assign Roles
		roleSvc.assignRoles(game);
		log.info(game.toString());
		
		// Start game
		gameSvc.startGame(game);
	}

}
