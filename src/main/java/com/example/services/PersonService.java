package com.example.services;

import com.example.dataaccess.MessageRepository;
import com.example.dataaccess.PersonRepository;
import com.example.entities.Message;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    PersonRepository personRepository;
    MessageRepository messageRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, MessageRepository messageRepository) {
        this.personRepository = personRepository;
        this.messageRepository = messageRepository;
    }
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long personId) {
        return personRepository.findById(personId).orElse(null);
    }

    public Person addPerson(Person person) {
        personRepository.save(person);
        return person;
    }

    public Person editPerson(Person person) {
        if(person.getId() == null){
            throw new IllegalArgumentException("The user's unique ID must be specified.");
        }
        if(person.getName() == null && person.getEmail() == null){
            throw new IllegalArgumentException("At least one of name and email must be specified to edit");
        }
        Optional<Person> specifiedPerson = personRepository.findById(person.getId());
        if (specifiedPerson.isEmpty()){
            throw new IllegalArgumentException("The specified ID was not found");
        }
        Person workingPerson = specifiedPerson.get();
        if (person.getName() != null && !person.getName().equals(workingPerson.getName())){
            workingPerson.setName(person.getName());
        }
        if (person.getEmail() != null && !person.getEmail().equals(workingPerson.getEmail())) {
            workingPerson.setEmail(person.getEmail());
        }
        personRepository.save(workingPerson);
        return workingPerson;
    }

    public String deletePerson(long id) {
        Optional<Person> specifiedPerson = personRepository.findById(id);
        if(specifiedPerson.isEmpty()){
            throw new IllegalArgumentException("The specified user ID does not exist");
        }
        else {
            Message[] deletedPersonMessages = messageRepository.findMessagesBySenderId(id);
            ArrayList<Long> messageIds = new ArrayList<>();
            for (Message message: deletedPersonMessages) {messageIds.add(message.getId());}
            messageRepository.deleteAllById(messageIds);
            personRepository.deleteById(id);
        }
        if (personRepository.findById(id).isEmpty()){
            return "User with id " + id + " and all their messages successfully deleted.";}
        else throw new RuntimeException("The deletion failed");
    }
}
