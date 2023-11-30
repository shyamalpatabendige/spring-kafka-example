package com.shyamalmadura.kafka.example.application.service;

import com.shyamalmadura.kafka.example.application.dto.User;
import com.shyamalmadura.kafka.example.repository.database.UserRepository;
import com.shyamalmadura.kafka.example.repository.database.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(final User user) {
        log.info("Saving user with id = {}", user.getUuid());
        UserEntity userEntity = UserEntity.builder()
                .id(user.getUuid())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        userRepository.save(userEntity);
    }

    public List<User> getUsers(final String firstName) {
        List<UserEntity> userEntities = userRepository.getByFirstNameIgnoreCaseOrderByFirstNameAscLastNameAsc(firstName);
        return userEntities.stream()
                .map(e ->  User.builder()
                        .uuid(e.getId())
                        .firstName(e.getFirstName())
                        .lastName(e.getLastName())
                        .build())
                .collect(Collectors.toList());
    }
}
