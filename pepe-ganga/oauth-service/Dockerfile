FROM openjdk:11
VOLUME /tmp
ADD ./target/oauth-service-1.0.jar oauth-service.jar
ENTRYPOINT ["java", "-jar", "/oauth-service.jar"]