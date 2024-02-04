package com.homeWorkKafka.Kafka.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {

    public static final String TOPIC_NAME = "kafka.example.topic";

    @KafkaListener(topics = TOPIC_NAME,
    topicPartitions = @TopicPartition(topic = TOPIC_NAME,
    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")))
    public void consume(String message) {
        log.info("Receive new message! {}", message);
    }

    @KafkaListener(topics = {TOPIC_NAME + ".message", TOPIC_NAME + ".booking"})
    public void messageAndBookTopics(@Payload String message,
                                     @Header(KafkaHeaders.RECEIVED_TOPIC) String receivedTopic,
                                     @Header(KafkaHeaders.RECEIVED_PARTITION) String receivedPartition,
                                     @Header(KafkaHeaders.OFFSET) String receivedOffset) {
        log.info("Recive from Topic {} by partition {}, offset {}: {}", receivedTopic, receivedPartition, receivedOffset, message);
    }
}
