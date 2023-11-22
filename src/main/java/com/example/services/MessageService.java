package com.example.services;

import com.example.dataaccess.MessageRepository;
import com.example.dataaccess.PersonRepository;
import com.example.entities.Message;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messageRepository;
    PersonRepository personRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, PersonRepository personRepository) {
        this.messageRepository = messageRepository;
        this.personRepository = personRepository;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(long messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        return message.orElse(null);
    }

    public List<Message> getAllMessagesBySenderEmail(String email) {
        return messageRepository.findMessagesBySenderEmailIgnoreCase(email);
    }

    public List<Message> getAllMessagesBySenderName(String name) {
        return messageRepository.findMessagesBySenderNameIgnoreCase(name);
    }

    public Message addMessage(Message message) {
        if (message.getId() != null) {
            throw new IllegalArgumentException("The unique ID is generated by the service and does not need to be provided.");
        }
        Optional<Person> proposedSender = personRepository.findById(message.getSender().getId());
        proposedSender.ifPresentOrElse(message::setSender, () -> {
            throw new IllegalArgumentException("The sender ID is not valid.");
        });
        messageRepository.save(message);
        return message;
    }

    public Message editMessage(Message message) {
        Optional<Message> specifiedMessage = messageRepository.findById(message.getId());
        if (specifiedMessage.isEmpty()) {
            throw new IllegalArgumentException("The specified message ID does not exist");
        }
        else{
            Person originalSender = specifiedMessage.get().getSender();
            Optional<Person> sender = personRepository.findById(originalSender.getId());
            sender.ifPresent(message::setSender);
        }
        messageRepository.save(message);
        return message;
    }

    public String deleteMessage(long id) {
        Optional<Message> specifiedMessage = messageRepository.findById(id);
        if(specifiedMessage.isEmpty()){
            throw new IllegalArgumentException("The specified message ID does not exist");
        }
        else messageRepository.deleteById(id);
        if (messageRepository.findById(id).isEmpty()){
        return "Message " + id + " successfully deleted.";}
        else throw new RuntimeException("The deletion failed");
    }
}
