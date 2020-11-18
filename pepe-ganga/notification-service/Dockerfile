FROM openjdk:11
VOLUME /tmp
ADD ./target/notification-service-1.0.jar notification-service.jar
ENTRYPOINT ["java", "-jar", "/notification-service.jar"]