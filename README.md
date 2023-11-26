# Spring Kafka Example
This Repo contains Kafka integration using Spring Boot Application

### Producer

Kafka producer is sending two types of message - Person and Orc. Person message is sent every 2 seconds, Orc is sent
every 5 seconds. 

### Consumer

Kafka consumer is adjusted only to Person message, each Orc message will be skipped after deserialization attempt.
