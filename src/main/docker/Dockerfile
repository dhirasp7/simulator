FROM openjdk:11.0.8-jre-slim

MAINTAINER Sanjeev Dhir "sanjeev.dhir@gmail.com"

ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /app/
ADD maven/simulator-${VERSION}.jar application.jar
EXPOSE 8080

CMD ["java", "-Dspring.profiles.acive=docker", "-jar", "/app/application.jar"]