package com.nus.iss.werewolf.model.messages;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SampleMessage {
    private String message;
    public SampleMessage(String message) {
        this.message = message;
    }
}