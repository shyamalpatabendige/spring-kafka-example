# Use Alpine-based OpenJDK 17 as the base image
FROM amazoncorretto:17.0.8-alpine3.18 as builder

WORKDIR /app
COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]