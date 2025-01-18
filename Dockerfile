# Use a Maven image to build the JAR
FROM maven:latest  AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use an OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target folder of the first stage
COPY --from=build /app/target/habit-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that your Spring Boot app runs on
EXPOSE 8087

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
