package unit.com.shyamalmadura.kafka.example.application.service;

import com.github.javafaker.Faker;
import com.shyamalmadura.kafka.example.application.dto.User;
import com.shyamalmadura.kafka.example.application.service.UserService;
import com.shyamalmadura.kafka.example.repository.database.UserRepository;
import com.shyamalmadura.kafka.example.repository.database.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void givenValidUser_whenPersists_returnNoError() {
        Faker faker = new Faker();
        User user = User.builder().uuid(UUID.randomUUID().toString())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();
        UserEntity userEntity = UserEntity.builder()
                .id(user.getUuid())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        userService.save(user);
        verify(userRepository).save(eq(userEntity));
    }

    @Test
    void givenValidUser_whenExceptionThrown_ThrowsException() {
        Faker faker = new Faker();
        User user = User.builder().uuid(UUID.randomUUID().toString())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();
        UserEntity userEntity = UserEntity.builder()
                .id(user.getUuid())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        when(userRepository.save(userEntity)).thenThrow(new RuntimeException("Error"));
        assertThrows(RuntimeException.class, () -> userService.save(user));
    }


}