package com.nus.iss.werewolf.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    // Tutorial
    // @MessageMapping("/message")
    // @SendTo("/topic/message")
    // public String sendMessage(@Payload SampleMessage message) throws JsonProcessingException{
    //     System.out.println("Message received from Client: " +message.getMessage());
    //     ObjectMapper om = new ObjectMapper();
    //     return om.writeValueAsString(new SampleMessage("test"));
    // }

    // @MessageMapping("/{gameName}/ack")
    // public void acknowledge(@DestinationVariable String gameName, @Payload String body, SimpMessageHeaderAccessor header){
    //     System.out.printf("\tClient Acknowledged! \n\tPath: %s \n\tBody: %s\n", gameName, body);
    //     System.out.println("Headers: " + header.getFirstNativeHeader("type"));
    // }
}
