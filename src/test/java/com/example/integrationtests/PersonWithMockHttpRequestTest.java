package com.example.integrationtests;

import com.example.controllers.GlobalControllerExceptionHandler;
import com.example.entities.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Sql("classpath:test-data.sql")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
 class PersonWithMockHttpRequestTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    GlobalControllerExceptionHandler globalControllerExceptionHandler;
    @Test
    public void testGettingAllPersons() throws Exception {
        String expectedString = """
                [{"id":1,"name":"Ed"},{"id":2,"name":"Dave"},{"id":3,"name":"Bill"},{"id":4,"name":"Doug"}]
                """;
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/persons"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(expectedString))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Person[] actualPersons = mapper.readValue(contentAsJson, Person[].class);
        Assertions.assertEquals("Doug",actualPersons[3].getName());
    }
    @Test
    public void testGettingPerson4() throws Exception {
        String expectedString = """
                {"id":4,"name":"Doug"}
                """;
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/persons/4"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(expectedString))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Person actualPerson = mapper.readValue(contentAsJson, Person.class);
        Assertions.assertEquals("Doug",actualPerson.getName());
    }
    @Test
    public void testGettingPerson0() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persons/0"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testPutPersonWithId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/persons")
                        .contentType("application/json")
                        .content("""
                                {
                                  "name": "Joe Bloggs",
                                  "email": "joe@example.com"
                                }
                                """))
                .andExpect(status().isBadRequest());
        Mockito.verify(globalControllerExceptionHandler,times(1)).handleInvalidRequest(any());
        }
}


