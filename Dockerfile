FROM eclipse-temurin:17-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} cloudgateway.jar
ENTRYPOINT ["java", "-jar", "/cloudgateway.jar"]
EXPOSE 9090