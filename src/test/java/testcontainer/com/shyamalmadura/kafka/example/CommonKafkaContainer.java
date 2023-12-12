package testcontainer.com.shyamalmadura.kafka.example;

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
        // createTopics(); // Needed if auto.create.topics.enable set to false

        return kafkaContainer;
    }

//    private static void createTopics() {
//        try (
//                AdminClient adminClient = AdminClient.create(
//                        ImmutableMap.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers())
//                );
//        ) {
//            Collection<NewTopic> topics = Collections.singletonList(new NewTopic("my-topic-1", 2, (short) 1));
//            adminClient.createTopics(topics).all().get(30, TimeUnit.SECONDS);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }

    @Override
    public String getBootstrapServers(){
        return kafka.getBootstrapServers();
    }
}
