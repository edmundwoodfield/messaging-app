package com.example.entities;

import com.example.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
public class Message {
    public Message(String content, Person sender) {
        this.content = content;
        this.sender = sender;
    }

    public Message() {
    }

    @Id
    @GeneratedValue(generator = "message_sequence")
    @SequenceGenerator(name = "message_sequence", initialValue = 100)
    @Schema(name = "id", description = "Unique ID of the message", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonView(Views.MessageEdit.class)
    private Long id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @JsonView({Views.MessageCreation.class, Views.MessageEdit.class})
    @Schema(name = "content", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }
    @JsonView(Views.MessageCreation.class)
    @ManyToOne
    @Schema(name = "sender", description = "Message sender", requiredMode = Schema.RequiredMode.REQUIRED)
    private Person sender;
}

