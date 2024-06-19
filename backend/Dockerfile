# Use the official Gradle image with JDK 17 for building the application
FROM gradle:7.5.1-jdk17 as build-stage

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and the build files
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy the source code
COPY src src

# Give permission to execute the Gradle wrapper
RUN chmod +x gradlew

# Build the application
RUN ./gradlew bootJar --no-daemon

# Use the official OpenJDK 17 as a parent image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build-stage /app/build/libs/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
