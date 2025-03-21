package com.placidotech.pasteleria.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendLogoutNotification() {
        messagingTemplate.convertAndSend("/topic/logout", "LOGOUT");
    }
}
