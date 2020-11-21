FROM openjdk:11
VOLUME /tmp
EXPOSE 8761
ADD ./target/eureka-service-1.0.jar eureka-service.jar
ENTRYPOINT ["java", "-jar", "/eureka-service.jar"]