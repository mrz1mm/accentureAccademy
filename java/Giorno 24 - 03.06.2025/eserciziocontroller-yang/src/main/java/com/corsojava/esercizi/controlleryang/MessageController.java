package com.corsojava.esercizi.controlleryang;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private final List<Message> messages = new ArrayList<>();
    // Utilizziamo un contatore atomico per generare ID unici per i messaggi
    private final AtomicLong idCounter = new AtomicLong();


    public MessageController() {
        messages.add(new Message(idCounter.incrementAndGet(), "Hello, World!"));
        messages.add(new Message(idCounter.incrementAndGet(), "Welcome to the Spring Boot application!"));
    }

    @RequestMapping("/messages")
    public List<Message> getAllMessages() {
        return messages;
    }

    @RequestMapping("/add-message")
    public Message addPredefiniteMessage() {
        Message newMessage = new Message(idCounter.incrementAndGet(), "This is a predefined message.");
        messages.add(newMessage);
        return newMessage;
    }

}
