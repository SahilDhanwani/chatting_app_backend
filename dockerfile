# Use Maven with JDK 14 to build the application
FROM maven:3.6.3-jdk-14-slim AS build

# Set the working directory
WORKDIR /usr/src/app

# Copy the pom.xml and source files
COPY pom.xml .
COPY src ./src

# Build the application using Maven
RUN mvn clean install -DskipTests

# Use OpenJDK image for the final app image
FROM openjdk:14-jdk-alpine

# Set the working directory
WORKDIR /usr/src/app

# Copy the built JAR file from the build image
COPY --from=build /usr/src/app/target/your-spring-boot-app.jar .

# Expose the port the app runs on
EXPOSE 8080

# Define the command to run the Spring Boot app
CMD ["java", "-jar", "your-spring-boot-app.jar"]
