package component.com.shyamalmadura.kafka.example.repository.database;

import com.github.javafaker.Faker;
import com.shyamalmadura.kafka.example.Application;
import com.shyamalmadura.kafka.example.repository.database.UserRepository;
import com.shyamalmadura.kafka.example.repository.database.entity.UserEntity;
import common.com.shyamalmadura.kafka.example.AbstractContainerLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
class UserRepositoryTest extends AbstractContainerLoader {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Faker faker;

    @Test
    void givenValidUser_whenPersist_ReturnUser() {

        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();

        UserEntity u = userRepository.save(user);

        assertThat(u.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(u.getLastName()).isEqualTo(user.getLastName());
        assertThat(u.getId()).isEqualTo(user.getId());

        List<UserEntity> userEntities = userRepository.getByFirstNameIgnoreCaseOrderByFirstNameAscLastNameAsc(user.getFirstName());

        assertAll( "Verify queried user entities",
                () -> assertThat(userEntities.size()).isEqualTo(1),
                () -> {
                    UserEntity ue = userEntities.get(0);
                    assertThat(ue.getFirstName()).isEqualTo(user.getFirstName());
                    assertThat(ue.getLastName()).isEqualTo(user.getLastName());
                    assertThat(ue.getId()).isEqualTo(user.getId());
                }
        );
    }

    @Test
    void givenInvalidUser_whenPersistFails_ReturnException() {
        Faker faker = new Faker();

        UserEntity user = UserEntity.builder().build();

        assertThrows(JpaSystemException.class, () ->userRepository.save(user));

    }

}