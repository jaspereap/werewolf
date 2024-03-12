package com.nus.iss.werewolf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.nus.iss.werewolf.model.messages.SampleMessage;
import com.nus.iss.werewolf.service.MessageHandler;

@Configuration
// @EnableWebSocketMessageBroker
@EnableWebSocket
// public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MessageHandler(), "/ws").setAllowedOrigins("*");
    }
    // @Override
    // public void configureMessageBroker(MessageBrokerRegistry registry) {
    //     registry.enableSimpleBroker("/topic");
    //     registry.setApplicationDestinationPrefixes("/app");
    // }

    // @Override
    // public void registerStompEndpoints(StompEndpointRegistry registry) {
    //     registry.addEndpoint("/ws")
    //             .setAllowedOrigins("http://localhost:4200");
    // }
}
