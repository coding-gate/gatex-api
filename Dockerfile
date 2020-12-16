FROM openjdk:8-jdk-alpine
RUN apk add --no-cache bash
ADD target/gatexapi.jar gatexapi.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","gatexapi.jar"]