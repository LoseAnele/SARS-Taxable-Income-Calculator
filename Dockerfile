# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Build the fat jar, skipping tests to save time during deployment
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
# Copy the built jar from the previous stage
COPY --from=build /app/target/app.jar app.jar
# Expose the port (Render sets this, but good for documentation)
EXPOSE 7070
# Run the application
CMD ["java", "-jar", "app.jar"]