package com.nus.iss.werewolf.model.messages.dtos;

import com.nus.iss.werewolf.model.Player;
import com.nus.iss.werewolf.model.PlayerState;

import lombok.Data;

@Data
public class PlayerDTO {
    String playerName;
    PlayerState playerState;

    public PlayerDTO(Player player) {
        this.playerName = player.getPlayerName();
        this.playerState = player.getPlayerState();
    }
}
