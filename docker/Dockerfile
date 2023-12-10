FROM openjdk:17
WORKDIR /application
ARG APP_JAR=*.jar
COPY ${APP_JAR} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]