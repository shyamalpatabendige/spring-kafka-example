package com.shyamalmadura.kafka.example.repository.producer;

import com.shyamalmadura.kafka.example.application.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserKafkaProducer {

    private final KafkaTemplate<String, User> kafkaTemplate;

    @Value("${spring.kafka.topics.user.details}")
    private String topic;

    @Value("${spring.kafka.replication.factor:1}")
    private int replicationFactor;

    @Value("${spring.kafka.partition.number:1}")
    private int partitionNumber;


    public void publish(final User user) {
        kafkaTemplate.send(topic, user.getUuid(), user);
        log.info("Published a message contains a user information with id {}, to {} topic", user.getUuid(), topic);
    }

//    @Bean
//    @Order(-1)
//    public NewTopic createNewTopic() {
//        return new NewTopic(topic, partitionNumber, (short) replicationFactor);
//    }
}
