package com.example.controllers;

import com.example.entities.Message;
import com.example.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class MessageControllerBasicSpringTest {
    @Autowired
    MessageController messageController;
    @MockBean
    MessageService messageService;

    @Test
    public void getContextAndTestBehaviour(){
        List<Message> messages = messageController.getAllMessages();
        verify(messageService,times(1)).getAllMessages();
    }

}
