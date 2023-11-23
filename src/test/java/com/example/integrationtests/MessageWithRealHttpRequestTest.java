package com.example.integrationtests;

import com.example.BarclayMessageApplication;
import com.example.dataaccess.Populator;
import com.example.entities.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;

 class MessageWithRealHttpRequestTest {

    ObjectMapper mapper = new ObjectMapper();
    @Disabled("This requires the real app to be running")
    @Test
    public void testGettingAllMessages() throws IOException {
// Run the main application first
        HttpUriRequest request = new HttpGet("http://localhost:8081/messages");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Message[] actualMessages = mapper.readValue(response.getEntity().getContent(), Message[].class);
        Assertions.assertEquals("This is also a message",actualMessages[1].getContent());

    }
    @Disabled("This requires the real app to be running")
    @Test
    public void testGettingMessage2() throws IOException {
// Run the main application first
        HttpUriRequest request = new HttpGet("http://localhost:8081/messages/2");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Message actualMessage = mapper.readValue(response.getEntity().getContent(), Message.class);
        Assertions.assertEquals("This is also a message",actualMessage.getContent());

    }

}
