# Getting Started


### How to run application

To run this application you need to have installed Docker with Docker Compose.

#### Start containers
```shell
docker-compose up -d
```
<details open>
<summary>This will start the containers</summary>

* ZooKeeper
* Schema Registry
* Kafka
* Kafdrop containers in the background.
    * You can then access Kafdrop at http://localhost:9000 in your web browser to monitor and interact with your Kafka cluster.
* Create Kafka Topics
* PostgreSQL instance
</details>

#### List running docker instances
```shell
docker-compose ps
```
#### Inspect created kafka Topics
```shell
docker logs create-kafka-topics
```
#### Start Application
```shell
./gradlew bootRun
```
<details open>
  <summary> Access Application </summary>
* http://localhost:8080/api-docs
* curl http://localhost:8080/actuator/metrics | jq
* curl http://localhost:8080/actuator/metrics/by-name | jq
</details>

<details open>
  <summary> Query PostgreSQL</summary>

#### Login to container
```shell
docker exec -it postgres-db bash
```
##### Login to Database
```
psql -U compose-postgres-usr -d compose-postgres
```
```shell
\dt
```
```shell
\d+ users
```
```shell
SELECT * FROM users;
```
</details>

#### Stop containers
```shell
docker-compose down
```
This command will stop and remove all containers, networks, and volumes defined in your docker-compose.yml file.
