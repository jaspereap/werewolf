package com.nus.iss.werewolf.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.nus.iss.werewolf.model.messages.SampleMessage;

@Controller
public class WebSocketController {

    @MessageMapping("/connected")
    public void sendMessage(SampleMessage message){
        System.out.println("RECEIVED!!!!");
        System.out.println(message);
    }
}
