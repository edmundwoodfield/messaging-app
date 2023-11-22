package com.example.services;

import com.example.controllers.PersonController;
import com.example.controllers.PersonControllerTest;
import com.example.dataaccess.PersonRepository;
import com.example.entities.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")

class PersonServiceTest {
    @Autowired
    PersonService personService;
    @MockBean
    PersonRepository mockPersonRepository;

    @Test
    void getAllPersons() {
        List<Person> mockPersons = PersonControllerTest.createPersonList();
        when(mockPersonRepository.findAll()).thenReturn(mockPersons);
        List<Person> actual = personService.getAllPersons();
        assertEquals(mockPersons,actual);
    }
    @Test
    void getPersonById() {
        Person mockPerson = new Person("Bill","bill@bill.com");
        when(mockPersonRepository.findById(any())).thenReturn(Optional.of(mockPerson));
        Person actual = personService.getPersonById(234L);
        assertEquals(mockPerson.getName(),actual.getName());
    }
}