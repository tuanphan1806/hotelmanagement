FROM openjdk:17

# ARG JAR_FILE=target/backend-0.0.1-SNAPSHOT.jar
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} backend.jar

ENTRYPOINT ["java", "-jar", "/backend.jar"]

EXPOSE 8080