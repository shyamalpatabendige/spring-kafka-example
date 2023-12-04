package integration.com.shyamalmadura.kafka.example;

import com.github.javafaker.Faker;
import com.shyamalmadura.kafka.example.Application;
import com.shyamalmadura.kafka.example.application.dto.User;
import com.shyamalmadura.kafka.example.application.service.UserService;
import com.shyamalmadura.kafka.example.common.db.CommonKafkaContainer;
import com.shyamalmadura.kafka.example.common.db.CommonPostgreSQLContainer;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

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
    void shouldGetAllCustomers() {
        User user = User.builder().uuid(UUID.randomUUID().toString())
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .build();
        userService.save(user);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/users/" + user.getFirstName())
                .then()
                .statusCode(200)
                .body(".", hasSize(1));
    }


}
