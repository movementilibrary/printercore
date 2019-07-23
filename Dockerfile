FROM openjdk:8-jdk-alpine

VOLUME /tmp

COPY build/libs/printer-core-api-VERSION-REVISION.jar print-core-0.0.1.jar 

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/print-core-0.0.1.jar"]
