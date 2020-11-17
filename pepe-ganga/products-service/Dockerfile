FROM openjdk:11
VOLUME /tmp
ADD ./target/products-service-1.0.jar products-service.jar
ENTRYPOINT ["java", "-jar", "/products-service.jar"]