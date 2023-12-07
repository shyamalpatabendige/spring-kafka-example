package testcontainer.com.shyamalmadura.kafka.example;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class CommonPostgreSQLContainer extends PostgreSQLContainer<CommonPostgreSQLContainer> {

    private static final String IMAGE_VERSION = "postgres:15.2";
    private static CommonPostgreSQLContainer postgres;

    private CommonPostgreSQLContainer() {
        super(IMAGE_VERSION);
    }

    public static CommonPostgreSQLContainer getInstance() {
        if (postgres == null) {
            postgres = new CommonPostgreSQLContainer();
        }
        return postgres;
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
