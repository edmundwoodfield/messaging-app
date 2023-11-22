package com.example.controllers;

import com.example.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc
class MessageControllerTestSpring {

    @MockBean
    MessageService mockMessageService;
    @Autowired
    MockMvc mockMvc;
    @Test
    void testGetAllMessages() throws Exception {
       MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages");
       mockMvc.perform(requestBuilder)
                       .andExpect(MockMvcResultMatchers.status().isOk());

       verify(mockMessageService,times(1)).getAllMessages();
    }
    @Test
    void testGetMessageById() throws Exception {
        Long messageId = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages/" + messageId);
        mockMvc.perform(requestBuilder);
        verify(mockMessageService,times(1)).getMessageById(messageId);
    }
}