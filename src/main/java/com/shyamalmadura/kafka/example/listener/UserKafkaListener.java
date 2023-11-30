package com.shyamalmadura.kafka.example.listener;

import com.shyamalmadura.kafka.example.application.dto.User;
import com.shyamalmadura.kafka.example.application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserKafkaListener {

    private final UserService userService;

    @KafkaListener(topics = "${spring.kafka.topics.user.details}",
            concurrency = "${spring.kafka.consumer.level.concurrency:3}")
    public void createUser(@Payload User user,
                                 @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                                 @Header(KafkaHeaders.OFFSET) Long offset) {
        log.info("Received a message contains a user information with id {}, from {} topic, " +
                "{} partition, and {} offset", user.getUuid(), topic, partition, offset);
        userService.save(user);
    }
}
