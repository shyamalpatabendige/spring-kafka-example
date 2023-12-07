package integration.com.shyamalmadura.kafka.example;

import com.github.javafaker.Faker;
import com.shyamalmadura.kafka.example.Application;
import com.shyamalmadura.kafka.example.application.dto.User;
import com.shyamalmadura.kafka.example.application.service.UserService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import testcontainer.com.shyamalmadura.kafka.example.CommonKafkaContainer;
import testcontainer.com.shyamalmadura.kafka.example.CommonPostgreSQLContainer;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
@ActiveProfiles({"test"})
public class UserControllerTest {

    @LocalServerPort
    private Integer port;

    @ClassRule
    public static PostgreSQLContainer<CommonPostgreSQLContainer> postgreSQLContainer = CommonPostgreSQLContainer.getInstance();

    @ClassRule
    public static CommonKafkaContainer kafkaContainer = CommonKafkaContainer.getInstance();

    @Autowired
    private UserService userService;
    @Autowired
    private Faker faker;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void givenFirstName_whenUserExists_thenReturnUser() {
        User user = User.builder().uuid(UUID.randomUUID().toString())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();
        userService.save(user);

        User[] users = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/users/" + user.getFirstName())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(User[].class);

        assertThat(users.length).isEqualTo(1);
        assertThat(users).allSatisfy( u -> {
           assertThat(u.getFirstName()).isEqualTo(user.getFirstName());
           assertThat(u.getLastName()).isEqualTo(user.getLastName());
           assertThat(u.getUuid()).isEqualTo(user.getUuid());
        });
    }

    @Test
    void givenUserCreated_whenKafkaPublishSuccess_thenReturnUser() {

        User user = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/users/random")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(User.class);


        User[] users = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/users/" + user.getFirstName())
                .then()
                .statusCode(200)
                .extract()
                .as(User[].class);

        assertThat(users.length).isEqualTo(1);
        assertThat(users).allSatisfy( u -> {
            assertThat(u.getFirstName()).isEqualTo(user.getFirstName());
            assertThat(u.getLastName()).isEqualTo(user.getLastName());
            assertThat(u.getUuid()).isEqualTo(user.getUuid());
        });
    }


}
