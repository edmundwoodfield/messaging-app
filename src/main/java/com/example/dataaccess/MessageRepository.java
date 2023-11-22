package com.example.dataaccess;

import com.example.entities.Message;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends ListCrudRepository<Message,Long> {
    List<Message> findMessagesBySenderEmailIgnoreCase(String email);
    List<Message> findMessagesBySenderNameIgnoreCase(String name);
    Message[] findMessagesBySenderId(long id);

    void deleteAllById(Message[] messages);
}
