package com.homeWorkKafka.Kafka.producer.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {

    private String messageText;
}
