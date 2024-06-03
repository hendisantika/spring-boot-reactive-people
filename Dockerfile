FROM eclipse-temurin:21-alpine
LABEL authors="hendisantika"
MAINTAINER hendisantika@yahoo.co.id

EXPOSE 8080
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/reactive-people-1.0.0.jar /app/reactive-people.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker", "-jar", "/app/reactive-people.jar"]
