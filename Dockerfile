# Build the application with a Java 21 Maven image.
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B -ntp verify

# Run as a non-root user in a smaller JRE image.
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN groupadd --system spring && useradd --system --gid spring spring
COPY --from=build /workspace/target/network-device-inventory-api-1.0.0.jar app.jar
USER spring
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
