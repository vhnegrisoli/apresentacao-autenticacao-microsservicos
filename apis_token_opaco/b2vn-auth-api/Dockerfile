FROM openjdk:11.0.5-jdk-slim
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/b2vn-auth-api.jar
COPY ${JAR_FILE} b2vn-auth-api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/b2vn-auth-api.jar"]