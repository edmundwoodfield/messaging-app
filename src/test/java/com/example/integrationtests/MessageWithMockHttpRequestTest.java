package com.example.integrationtests;

import com.example.entities.Message;
import com.example.entities.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Sql("classpath:test-data.sql")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
public class MessageWithMockHttpRequestTest {

    @Autowired
    MockMvc mockMvc;

    public static final String expectedString = """
                [{"id":1,"content":"Message 1"},{"id":2,"content":"Message 2"},{"id":3,"content":"Message 3"},{"id":4,"content":"Message 4"}]
                """;
    public static final String expectedStringMessage3 = """
            {"id":3,"content":"Message 3","sender":{"id":2,"name":"Dave","email":"dave@dave.com"}}
            """;
    @Test
    public void testGettingAllMessages() throws Exception {
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/messages"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Message[] actualMessages = mapper.readValue(contentAsJson, Message[].class);
        Assertions.assertEquals("Message 2",actualMessages[1].getContent());
    }
    @Test
    public void testGettingAllMessagesByEmail() throws Exception {
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/messages/sender/email/dave@dave.com"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Message[] actualMessages = mapper.readValue(contentAsJson, Message[].class);
        Assertions.assertEquals("Message 5",actualMessages[1].getContent());
    }
    @Test
    public void testGettingAllMessagesByName() throws Exception {
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/messages/sender/name/dave"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Message[] actualMessages = mapper.readValue(contentAsJson, Message[].class);
        Assertions.assertEquals("Message 5",actualMessages[1].getContent());
    }
    @Test
    public void testGettingMessage3() throws Exception {
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/messages/3"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(expectedStringMessage3))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Message actualMessage = mapper.readValue(contentAsJson, Message.class);
        Assertions.assertEquals("Message 3",actualMessage.getContent());
    }
    @Test
    public void testGettingMessage0() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/messages/0"))
                        .andExpect(status().isNotFound());
    }
    @Test
    public void testPostingMessage() throws Exception {
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.post("/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                  "content": "posted message",
                                  "sender": {
                                    "id": 1
                                  }
                                }
                                """))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Message actualMessage = mapper.readValue(contentAsJson, Message.class);
        Assertions.assertEquals("posted message",actualMessage.getContent());
    }
}
