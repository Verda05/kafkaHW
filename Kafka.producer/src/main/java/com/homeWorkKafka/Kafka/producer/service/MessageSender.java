package com.homeWorkKafka.Kafka.producer.service;

import com.homeWorkKafka.Kafka.producer.config.KafkaProducerConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageSender {

    private static final Random RA = new Random();
    private static final List<String> KEYS = new ArrayList<>() {{
        add("FIRST");
        add("MAIN");
        add("MESSAGE");

    }};

    KafkaTemplate<String, String> template;

    public boolean send(String massage,String topicName, Integer partition) {
        val key = KEYS.get(RA.nextInt(KEYS.size()));
        val future = template.send(
                topicName,
                partition == null ? 0 : partition,
                key, massage);
        try {
            val result = future.get(1, TimeUnit.SECONDS);
            log.info("Successful send to {} by key {} with offset {} to partition {}",
                    result.getProducerRecord().topic(), key,
                    result.getRecordMetadata().offset(), result.getRecordMetadata().partition());
            return true;
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            log.error("Cannot send message to Kafka Topic {}", KafkaProducerConfig.TOPIC_NAME, e);
        }
        return false;
    }
}
