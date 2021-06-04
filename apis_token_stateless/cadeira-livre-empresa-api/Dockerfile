FROM maven:3.6.3-jdk-11 AS MAVEN_BUILD
COPY ./ ./
RUN mvn clean install

FROM openjdk:11
EXPOSE 8095
COPY --from=MAVEN_BUILD /target/cadeira-livre-empresa-api-0.0.1.jar /cadeira-livre-empresa-api-0.0.1.jar
CMD ["java", "-jar", "/cadeira-livre-empresa-api-0.0.1.jar"]

#FROM openjdk:11.0.5-jdk-slim
#VOLUME /tmp
#EXPOSE 8080
#ARG JAR_FILE=target/cadeira-livre-empresa-api-0.0.1.jar
#COPY ${JAR_FILE} cadeira-livre-empresa-api-0.0.1.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cadeira-livre-empresa-api-0.0.1.jar"]