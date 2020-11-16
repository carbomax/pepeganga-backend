FROM openjdk:11
VOLUME /tmp
EXPOSE 9999
ADD ./target/zuul-service-1.0.jar zuul-service.jar
ENTRYPOINT ["java", "-jar", "/zuul-service.jar"]