# Use a base image with Java and Maven pre-installed
FROM maven:3.9.5-amazoncorretto-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the project
RUN mvn package

# Use a lightweight base image with Java to run the application
FROM amazoncorretto:17-alpine3.18

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/kafkaesque-1.0.0.jar .

# Set the command to run the application
CMD ["java", "-jar", "kafkaesque-1.0.0.jar"]
