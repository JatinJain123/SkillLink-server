# Use OpenJDK 17 with Alpine Linux (small image)
FROM openjdk:17-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the jar file into the container
COPY target/SkillLink-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 (Spring Boot default)
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
