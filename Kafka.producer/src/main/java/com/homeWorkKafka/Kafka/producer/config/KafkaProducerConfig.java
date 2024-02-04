package com.homeWorkKafka.Kafka.producer.config;

import lombok.val;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;

@Configuration
public class KafkaProducerConfig {

    public static final String TOPIC_NAME = "kafka.example.topic";
    public static final String TOPIC_NAME_MESSAGE = TOPIC_NAME + ".message";
    public static final String TOPIC_NAME_BOOKING = TOPIC_NAME + ".booking";

    @Value("${spring.kafka.bootstrap-servers}")
    private String serverAdress;

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(TOPIC_NAME)
                .replicas(1)
                .partitions(10)
                .build();

    }

    @Bean
    public NewTopic kafkaMessageTopic() {
        return TopicBuilder.name(TOPIC_NAME_MESSAGE)
                .replicas(1)
                .partitions(10)
                .build();

    }

    @Bean
    public NewTopic KafkaBookingTopic() {
        return TopicBuilder.name(TOPIC_NAME_BOOKING)
                .replicas(1)
                .build();

    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        val config = new HashMap<String, Object>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverAdress);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaStringTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
