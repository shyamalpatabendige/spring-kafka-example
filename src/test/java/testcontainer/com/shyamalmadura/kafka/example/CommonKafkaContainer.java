package testcontainer.com.shyamalmadura.kafka.example;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class CommonKafkaContainer extends KafkaContainer {

    private static final String IMAGE_VERSION = "confluentinc/cp-kafka";
    private static CommonKafkaContainer kafkaContainer;
    private static KafkaContainer kafka;

    private CommonKafkaContainer() {
        kafka = new KafkaContainer(DockerImageName.parse(IMAGE_VERSION))
                .withEmbeddedZookeeper();
    }

    public static CommonKafkaContainer getInstance() {
        if (kafkaContainer == null) {
            kafkaContainer = new CommonKafkaContainer();
            kafka.start();
        }
        return kafkaContainer;
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
