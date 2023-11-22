package com.example.services;

import com.example.dataaccess.MessageRepository;
import com.example.entities.Message;
import com.example.entities.Message;
import com.example.entities.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTestNoSpring {

    public static List<Message> createMessageList(){
        ArrayList<Message> messageArrayList = new ArrayList<>();
        Message message1 = new Message("Hi",new Person());
        Message message2 = new Message("Hey",new Person());
        Message message3 = new Message("Yo",new Person());
        messageArrayList.add(message1);
        messageArrayList.add(message2);
        messageArrayList.add(message3);
        return messageArrayList;
    }
    MessageRepository mockMessageRepository = mock(MessageRepository.class);

    @Test
    void getAllMessages() {
        List<Message> mockMessages = createMessageList();
        when(mockMessageRepository.findAll()).thenReturn(mockMessages);
        List<Message> actualMessages = mockMessageRepository.findAll();
        assertEquals(mockMessages,actualMessages);
    }
    @Test
    void testRepoCalled(){
        List<Message> actualMessages = mockMessageRepository.findAll();
        verify(mockMessageRepository, times(1)).findAll();
    }
}