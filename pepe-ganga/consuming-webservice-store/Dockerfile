FROM openjdk:11
VOLUME /tmp
ADD ./target/consuming-webservice-store-1.0.jar consuming-webservice-store.jar
ENTRYPOINT ["java", "-jar", "/consuming-webservice-store.jar"]