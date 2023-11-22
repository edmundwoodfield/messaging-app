package com.example.controllers;

import com.example.entities.Person;
import com.example.errors.InvalidRequestException;
import com.example.errors.ItemNotFoundException;
import com.example.views.Views;
import com.example.services.PersonService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
    PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("")
    @Operation(summary = "return all users")
    public List<Person> getAllMessages() {
        return personService.getAllPersons();
    }

    @GetMapping("/{personId}")
    @Operation(summary = "return user by ID")
    public Person getPersonById(@PathVariable Long personId) {
        Person person = personService.getPersonById(personId);
        if (person == null) {
            throw new ItemNotFoundException(HttpStatus.NOT_FOUND, "User not found");
        }
        return person;
    }

    @PostMapping("")
    @Operation(summary = "create a new user")
    @ResponseStatus(HttpStatus.CREATED)
    public Person addPerson(@RequestBody @JsonView(Views.PersonCreation.class) Person person) {
        Person newPerson;
        try {
            newPerson = personService.addPerson(person);
        } catch (Exception e) {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return newPerson;
    }

    @PutMapping("")
    @Operation(summary = "edit a user", description = "This is used to edit the name and/or email address associated with a user ID.")
    public Person editPerson(@RequestBody Person person) {
        Person editedPerson;
        try {
            editedPerson = personService.editPerson(person);
        } catch (Exception e) {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return editedPerson;
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "delete a user by user ID. This also deletes all their previous messages.")
    @Parameter(name="id")
    public String deletePerson(@PathVariable long id){
        return personService.deletePerson(id);
    }
}
