package com.corsojava.esercizi.service.naruto.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.corsojava.esercizi.service.naruto.dto.Message;

import jakarta.annotation.PostConstruct;

@Repository
public class MessageRepository {
    private final List<Message> messages = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    @PostConstruct
    public void init() {
        messages.add(new Message(idCounter.incrementAndGet(), "Hello, Naruto!"));
        messages.add(new Message(idCounter.incrementAndGet(), "Welcome to the Hidden Leaf Village!"));
    }

    public List<Message> findAll() {
        return new ArrayList<>(messages);
    }

    public Message save(Message message) {
        if (message.getId() == 0) {
            message.setId(idCounter.incrementAndGet());
            messages.add(message);
        } else {
            messages.removeIf(m -> m.getId() == message.getId());
            messages.add(message);
        }
        return message;
    }

    public Optional<Message> findById(long id) {
        return messages.stream()
                       .filter(message -> message.getId() == id)
                       .findFirst();
    }

    public void deleteById(long id) {
        messages.removeIf(message -> message.getId() == id);
    }

}
