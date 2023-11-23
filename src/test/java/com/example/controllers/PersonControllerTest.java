package com.example.controllers;

import com.example.entities.Person;
import com.example.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc
 class PersonControllerTest {
    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    PersonService mockPersonService;
    @Autowired
    MockMvc mockMvc;
    public static List<Person> createPersonList(){
        ArrayList<Person> personArrayList = new ArrayList<>();
        Person person1 = new Person("Dave","dave@dave.com");
        Person person2 = new Person("Bill","bill@bill.com");
        Person person3 = new Person("Doug","doug@doug.com");
        personArrayList.add(person1);
        personArrayList.add(person2);
        personArrayList.add(person3);
        return personArrayList;
    }
    @Test
    void getAllPersons() throws Exception {
        when(mockPersonService.getAllPersons()).thenReturn(createPersonList());
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/persons");
            ResultActions resultActions = mockMvc.perform(requestBuilder);
            verify(mockPersonService,times(1)).getAllPersons();
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Person[] actualPersons = mapper.readValue(contentAsString,Person[].class);
        assertEquals(3,actualPersons.length);
    }
    @Test
    void getPersonById() throws Exception {
        Long personId = 123L;
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/persons/" + personId);
            mockMvc.perform(requestBuilder);
            verify(mockPersonService,times(1)).getPersonById(personId);
        }

}
