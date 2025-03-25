package ru.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dto.Message;

import java.time.LocalDateTime;
import java.util.*;


@RestController
public class MessageController {

    private List<Message> messages = new ArrayList<>(Arrays.asList(
        new Message(1, "Первое сообщение", "Очень важное текст 1", LocalDateTime.of(2024, 12, 19, 12, 23)),
        new Message(2, "Второе сообщение", "Очень важное текст 2", LocalDateTime.of(2025, 01, 02, 12, 42)),
        new Message(3, "Третье сообщение", "Очень важное текст 3", LocalDateTime.of(2025, 01, 20, 9, 32)),
        new Message(4, "Четвертое сообщение", "Очень важное текст 4", LocalDateTime.of(2025, 02, 28, 17, 3))
    ));

    @GetMapping("/message")
    public Iterable<Message> getMessage() {
        return messages;
    }

    @GetMapping("/message/{id}")
    public Optional<Message> findMessageById(@PathVariable("id") int id) {
        return messages.stream().filter(m -> m.getId() == id).findFirst();
    }

    @PostMapping("/message")
    public Message addMessage(@RequestBody Message message) {
        messages.add(message);
        return message;
    }

    @PutMapping("/message/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable("id") int id, @RequestBody Message message) {
        int index = -1;
        for(Message p : messages) {
            if (p.getId() == id) {
                index = messages.indexOf(p);
                messages.set(index, message);
            }
        }
        return index == -1 ? new ResponseEntity<>(addMessage(message), HttpStatus.CREATED) : new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/message/{id}")
    public void deleteMessage(@PathVariable("id") int id) {
        messages.removeIf(m -> m.getId() == id);
    }
}
