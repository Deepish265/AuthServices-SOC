FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} auth-service.jar
ENTRYPOINT ["java","-jar","auth-service.jar"]