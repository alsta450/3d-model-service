# Start with a base image containing Java runtime
FROM openjdk:21-oracle

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=./target/3d-model-service-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} 3d-model-service-0.0.1-SNAPSHOT.jar

# Run the jar file
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /3d-model-service-0.0.1-SNAPSHOT.jar"]

LABEL version="2.1.0"