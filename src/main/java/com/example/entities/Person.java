package com.example.entities;

import com.example.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {
    public Person(){}
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
        sentMessages = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(generator = "person_sequence")
    //the following parameter ensures the test data does not collide with new IDs
    @SequenceGenerator(name="person_sequence", initialValue = 100)
    @JsonView(Views.MessageCreation.class)
    @Schema(name = "id", description = "unique ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @JsonView(Views.PersonCreation.class)
    @Schema(name = "name", description =  "Name", example = "Joe Bloggs",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;
    @JsonView({Views.PersonCreation.class})
    @Schema(name = "email", description = "Email address", example = "joe@example.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String email;
}
