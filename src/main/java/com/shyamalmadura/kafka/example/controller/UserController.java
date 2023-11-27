package com.shyamalmadura.kafka.example.controller;

import com.github.javafaker.Faker;
import com.shyamalmadura.kafka.example.application.service.UserService;
import com.shyamalmadura.kafka.example.dto.User;
import com.shyamalmadura.kafka.example.repository.producer.UserKafkaProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "User", description = "User APIs")
public class UserController {

    private final UserKafkaProducer kafkaProducer;

    private final UserService userService;

    private final Faker faker;

    public UserController(UserKafkaProducer kafkaProducer, UserService userService) {
        this.kafkaProducer = kafkaProducer;
        this.userService = userService;
        faker = new Faker();
    }

    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create a user", description = "Creates a random user and write it to Kafka which is consumed by the listener")
    public void generateRandomUser() {
        kafkaProducer.writeToKafka(new User(UUID.randomUUID().toString(), faker.name().firstName(), faker.name().lastName()));
    }

    @GetMapping("/{firstName}")
    @ResponseStatus
    @Operation(summary = "Create a user", description = "Returns a list of users that matchers the given name")
    public List<User> getUsers(@PathVariable(name = "firstName") String name) {
        return userService.getUsers(name);
    }
}