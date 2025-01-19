# Base image
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/original-webserver.jar /app/original-webserver.jar

# Set the entrypoint
ENTRYPOINT ["java", "-jar", "target/webserver.jar"]

# Set the default command
CMD ["--help"]