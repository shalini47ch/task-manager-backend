# Step 1: Build stage using official Maven image (with JDK 17)
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml first to leverage Docker cache
COPY pom.xml .
COPY src ./src

# Build the project and skip tests
RUN mvn clean package -DskipTests

# Step 2: Runtime stage using lightweight JDK
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
