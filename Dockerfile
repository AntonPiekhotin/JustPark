FROM openjdk:21-jdk-temurin

# Copy the packaged jar file into the container at /app
COPY target/*.jar /app.jar

# Run the jar file
CMD ["java", "-jar", "app.jar"]