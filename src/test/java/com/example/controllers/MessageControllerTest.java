package com.example.controllers;

import com.example.entities.Message;
import com.example.entities.Person;
import com.example.services.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
public class MessageControllerTest {
    MessageService mockMessageService = mock(MessageService.class);
    MessageController messageController = new MessageController(mockMessageService);
@BeforeEach
void resetMock(){
    reset(mockMessageService);

}
    @Test
    void getAllMessages() {

        List<Message> messages = messageController.getAllMessages();

        verify(mockMessageService, times(1)).getAllMessages();


    }
    @Test
    void getMessageById(){
        when(mockMessageService.getMessageById(anyLong())).thenReturn(new Message("blah",new Person()));
        Message message = messageController.getMessageById(123L);
        verify(mockMessageService,times(1)).getMessageById(123L);

    }
}