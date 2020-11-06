FROM openjdk:11
VOLUME /tmp
ADD ./target/consuming-webservice-store-1.0.jar consuming-service.jar
ENTRYPOINT ["java", "-jar", "/consuming-service.jar"]