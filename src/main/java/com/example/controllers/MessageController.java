package com.example.controllers;

import com.example.entities.Message;
import com.example.errors.InvalidRequestException;
import com.example.errors.ItemNotFoundException;
import com.example.views.Views;
import com.example.services.MessageService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    MessageService messageService;
    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("")
    @Operation(summary = "return all messages")
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }
    @GetMapping("/{messageId}")
    @Operation(summary = "return a specific message by ID")
    @Parameter(name="messageId",description = "The unique ID for the message",example = "1")
    public Message getMessageById(@PathVariable long messageId){
        Message message = messageService.getMessageById(messageId);
        if (message == null){
            throw new ItemNotFoundException(HttpStatus.NOT_FOUND,"Message not found");
        }
        return message;
    }
    @GetMapping("/sender/email/{email}")
    @Operation(summary = "return all the messages sent by a user identified by email address")
    @Parameter(name="email",example = "joe@example.com")
    public List<Message> getMessagesBySenderEmail(@PathVariable String email){
        List<Message> messageList = messageService.getAllMessagesBySenderEmail(email);
        if (messageList.isEmpty()){
            throw new ItemNotFoundException(HttpStatus.NOT_FOUND,"No sent messages found for "+ email);
        }
        return messageList;
    }
    @GetMapping("/sender/name/{name}")
    @Operation(summary = "return all the messages sent by a user identified by name")
    @Parameter(name="name",description = "not case sensitive", example = "Joe Bloggs")
    public List<Message> getMessagesBySenderName(@PathVariable String name){
        List<Message> messageList = messageService.getAllMessagesBySenderName(name);
        if (messageList.isEmpty()){
            throw new ItemNotFoundException(HttpStatus.NOT_FOUND,"No sent messages found for "+ name);
        }
        return messageList;
    }

    @PostMapping("")
    @Operation(summary = "create a new message", description = "This is used to send a new message from a specified user ID.")
    @ResponseStatus(HttpStatus.CREATED)
    public Message addMessage(@RequestBody @JsonView(Views.MessageCreation.class) Message message) {
        Message newMessage;
        try {
            newMessage = messageService.addMessage(message);
        } catch (Exception e) {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return newMessage;
    }
    @PutMapping("")
    @Operation(summary = "edit a message", description = "This is used to edit the content of a specified message.")
    public Message editMessage(@RequestBody @JsonView(Views.MessageEdit.class) Message message){
        Message updatedMessage;
        try {
            updatedMessage = messageService.editMessage(message);
        }
        catch (Exception e){
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return updatedMessage;
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "delete a message by message ID")
    @Parameter(name="id")
    public String deleteMessage(@PathVariable long id){
        return messageService.deleteMessage(id);
    }
}
