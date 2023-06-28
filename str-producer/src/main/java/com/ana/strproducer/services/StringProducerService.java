package com.ana.strproducer.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class StringProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send("str-topic", message).addCallback(
            success -> {
                if(success != null) {
                    log.info("Sent message: {}", message);
                    log.info("Partition: {}, Offset: {}",
                        success.getRecordMetadata().partition(),
                        success.getRecordMetadata().offset());
                }
            },
            error -> log.error("Error sending message: {}", error.getMessage()));
    }
}
