package com.example.integrationtests;

import com.example.entities.Message;
import com.example.entities.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PersonWithRealHttpRequestTest {

    ObjectMapper mapper = new ObjectMapper();
    @Disabled
    @Test
    public void testGettingAllPersons() throws IOException {
// Run the main application first
        HttpUriRequest request = new HttpGet("http://localhost:8080/persons");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Person[] actualPersons = mapper.readValue(response.getEntity().getContent(), Person[].class);
        Assertions.assertEquals("Bill",actualPersons[2].getName());

    }
    @Disabled
    @Test
    public void testGettingPerson3() throws IOException {
// Run the main application first
        HttpUriRequest request = new HttpGet("http://localhost:8080/persons/3");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Person actualPersons = mapper.readValue(response.getEntity().getContent(), Person.class);
        Assertions.assertEquals("Bill",actualPersons.getName());

    }
}
