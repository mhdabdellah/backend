#FROM ubuntu:latest
#LABEL authors="mohamedabdellahiblal"
#
#ENTRYPOINT ["top", "-b"]

# Dockerfile
# Build stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Create a non-root user and switch to it
#RUN addgroup --system spring && adduser --system --ingroup spring spring
#USER spring:spring

# Set environment variables
#ENV SPRING_PROFILES_ACTIVE=prod
#ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/mydb
#ENV SPRING_DATASOURCE_USERNAME=admin
#ENV SPRING_DATASOURCE_PASSWORD=secret

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]