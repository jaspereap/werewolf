package com.nus.iss.werewolf.controller;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.iss.werewolf.model.messages.SampleMessage;

@Controller
public class MessageController {

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public String sendMessage(@Payload SampleMessage message) throws JsonProcessingException{
        System.out.println("Message received from Client: " +message.getMessage());
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(new SampleMessage("test"));
    }

    @MessageMapping("/{gameName}/ack")
    public void acknowledge(@DestinationVariable String gameName, @Payload String playerName){
        System.out.printf("\tClient Acknowledged! \n\tGame: %s \n\tPlayer: %s\n", gameName, playerName);
    }
}
