package com.corsojava.esercizi.service.naruto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.corsojava.esercizi.service.naruto.dto.Message;
import com.corsojava.esercizi.service.naruto.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    public Optional<Message> getMessageById(long id) {
        return messageRepository.findById(id);
    }

    public void deleteMessage(long id) {
        messageRepository.deleteById(id);
    }

}
