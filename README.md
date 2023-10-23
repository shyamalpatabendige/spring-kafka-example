# spring-kafka-example
This Repo contains Kafka integration using Spring Boot Application


### How to run application

To run application you need to have installed Docker with Docker Compose. 

After clone set `services.kafka.environment.KAFKA_ADVERTISED_HOST_NAME` in `docker-compose.yml` to your Docker instance IP address and then type  

```shell
docker-compose up -d
```

This will start the ZooKeeper, Kafka, and Kafdrop containers in the background. You can then access Kafdrop at http://localhost:9000 in your web browser to monitor and interact with your Kafka cluster.


```shell
docker-compose down
```

This command will stop and remove all containers, networks, and volumes defined in your docker-compose.yml file.
### Producer

Kafka producer is sending two types of message - Person and Orc. Person message is sent every 2 seconds, Orc is sent
every 5 seconds. 

### Consumer

Kafka consumer is adjusted only to Person message, each Orc message will be skipped after deserialization attempt.
