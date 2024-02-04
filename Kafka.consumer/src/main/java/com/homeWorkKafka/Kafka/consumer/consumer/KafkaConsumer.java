package com.homeWorkKafka.Kafka.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {

    public static final String TOPIC_NAME = "kafka.example.topic";

    @KafkaListener(topics = TOPIC_NAME)
    public void consume(String message){
        log.info("Receive new message! {}", message);
    }
}
