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

 class PersonWithRealHttpRequestTest {

    ObjectMapper mapper = new ObjectMapper();
    @Disabled("This requires the real app to be running")
    @Test
    public void testGettingAllPersons() throws IOException {
// Run the main application first
        HttpUriRequest request = new HttpGet("http://localhost:8081/persons");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Person[] actualPersons = mapper.readValue(response.getEntity().getContent(), Person[].class);
        Assertions.assertEquals("Bill",actualPersons[2].getName());

    }
    @Disabled("This requires the real app to be running")
    @Test
    public void testGettingPerson3() throws IOException {
// Run the main application first
        HttpUriRequest request = new HttpGet("http://localhost:8081/persons/3");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Person actualPersons = mapper.readValue(response.getEntity().getContent(), Person.class);
        Assertions.assertEquals("Bill",actualPersons.getName());

    }
}
