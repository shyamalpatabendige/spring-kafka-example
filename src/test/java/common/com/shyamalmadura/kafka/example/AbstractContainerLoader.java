package common.com.shyamalmadura.kafka.example;

import org.junit.ClassRule;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import testcontainer.com.shyamalmadura.kafka.example.CommonKafkaContainer;
import testcontainer.com.shyamalmadura.kafka.example.CommonPostgreSQLContainer;

public abstract class AbstractContainerLoader {

    @ClassRule
    public static PostgreSQLContainer<CommonPostgreSQLContainer> postgreSQLContainer = CommonPostgreSQLContainer.getInstance();

    @ClassRule
    public static CommonKafkaContainer kafkaContainer = CommonKafkaContainer.getInstance();


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);

        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.producer.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.consumer.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }
}
