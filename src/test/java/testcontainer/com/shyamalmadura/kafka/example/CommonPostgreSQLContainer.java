package testcontainer.com.shyamalmadura.kafka.example;

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
            postgres.start();
        }
        return postgres;
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
