package com.homeWorkKafka.Kafka.producer.controller;

import com.homeWorkKafka.Kafka.producer.model.Message;
import com.homeWorkKafka.Kafka.producer.service.MessageSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SenderController {


    MessageSender messageSender;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestParam(value = "part", required = false) Integer partition,
                                       @RequestParam(value = "topic") String topicName,
                                       @RequestBody Message message) {
        if (messageSender.send(message.getMessageText(), topicName, partition)) {
            return ResponseEntity.ok("ok");
        }
        return status(INTERNAL_SERVER_ERROR)
                .body("kafka ins't available");
    }
}
