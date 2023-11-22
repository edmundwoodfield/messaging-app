package com.example.dataaccess;

import com.example.entities.Message;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class Populator {
    MessageRepository messageRepository;
    PersonRepository personRepository;
    @Autowired
    public Populator(MessageRepository messageRepository, PersonRepository personRepository){
        this.messageRepository = messageRepository;
        this.personRepository = personRepository;
    }
    @EventListener(ContextRefreshedEvent.class)
    public void populate(){
        Person ed = new Person("Ed","ed@ed.com");
        personRepository.save(ed);
        Person dave = new Person("Dave","dave@dave.com");
        personRepository.save(dave);
        Person bill = new Person("Bill","bill@bill.com");
        personRepository.save(bill);
        Person doug = new Person("Doug","doug@doug.com");
        personRepository.save(doug);
        Person joe = new Person("Joe Bloggs","joebloggs@joe.com");
        personRepository.save(joe);
        Message message = new Message("This is a message",dave);
        messageRepository.save(message);
        message = new Message("This is also a message",doug);
        messageRepository.save(message);
        message = new Message("This is a message from Joe", joe);
        messageRepository.save(message);
    }
}
