package com.nus.iss.werewolf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nus.iss.werewolf.service.GameService;
import com.nus.iss.werewolf.service.LobbyService;
import com.nus.iss.werewolf.service.MessageService;
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
	@Autowired
	MessageService msgSvc;
	public static void main(String[] args) {
		SpringApplication.run(WerewolfApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Current Running Games: " + lobbySvc.getGames());
	}

}
