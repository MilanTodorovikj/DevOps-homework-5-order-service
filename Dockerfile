FROM openjdk:17-jdk-slim

WORKDIR /app
COPY target/homework-4-order-service-1.0.0.jar app.jar
EXPOSE 8084

ENTRYPOINT ["java", "-jar", "/app/app.jar"]