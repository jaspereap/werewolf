package com.nus.iss.werewolf.model.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameRequest {
    String playerName;
    String gameName;
}
