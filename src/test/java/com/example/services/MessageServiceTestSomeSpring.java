package com.example.services;

import com.example.dataaccess.MessageRepository;
import com.example.entities.Message;
import com.example.entities.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class MessageServiceTestSomeSpring {
    @Autowired
    MessageService messageService;

    @MockBean
    MessageRepository mockMessageRepository;

    @Test
    void getAllMessages() {
        List<Message> mockMessages = MessageServiceTestNoSpring.createMessageList();
        when(mockMessageRepository.findAll()).thenReturn(mockMessages);
        List<Message> actualMessages = messageService.getAllMessages();
        assertEquals(mockMessages,actualMessages);
    }
    @Test
    void getMessageById() {
        Message expected = new Message("awkward no ID message",new Person());
        when(mockMessageRepository.findById(any())).thenReturn(Optional.of(expected));
        Message actualMessage = messageService.getMessageById(1);
        assertEquals(expected.getContent(),actualMessage.getContent());
    }
}