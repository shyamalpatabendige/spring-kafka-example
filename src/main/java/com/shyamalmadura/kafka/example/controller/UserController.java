package com.shyamalmadura.kafka.example.controller;

import com.github.javafaker.Faker;
import com.shyamalmadura.kafka.example.application.dto.User;
import com.shyamalmadura.kafka.example.application.service.UserService;
import com.shyamalmadura.kafka.example.repository.producer.UserKafkaProducer;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@ResponseBody
@Tag(name = "User", description = "User APIs")
public class UserController {

    private final ObservationRegistry observationRegistry;
    private final UserKafkaProducer kafkaProducer;
    private final UserService userService;
    private final Faker faker;

    @GetMapping("/random")
    @Operation(summary = "Create a user", description = "Creates a random user and write it to Kafka which is consumed by the listener")
    public User generateRandomUser() {
        User user = User.builder().uuid(UUID.randomUUID().toString())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();
        kafkaProducer.publish(user);
        return Observation
                .createNotStarted("by-user", observationRegistry)
                .observe( () -> user);
    }

    @GetMapping("/{firstName}")
    @Operation(summary = "Get Users", description = "Returns a list of users that matchers the given name")
    public Iterable<User> getUsers(@PathVariable(name = "firstName") String name) {
        Assert.hasLength(name.trim(), "Input cannot be empty or null");
        return Observation
                .createNotStarted("by-firstname", observationRegistry)
                .observe(() -> userService.getUsers(name));
    }
}