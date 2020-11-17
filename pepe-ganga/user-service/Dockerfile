FROM openjdk:11
VOLUME /tmp
ADD ./target/user-service-1.0.jar user-service.jar
ENTRYPOINT ["java", "-jar", "/user-service.jar"]