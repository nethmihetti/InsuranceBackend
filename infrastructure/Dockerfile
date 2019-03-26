FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD /build/libs/insuranceRESTServer-0.0.1-SNAPSHOT.jar insuranceRESTServer.jar
ENTRYPOINT ["java", "-jar", "insuranceRESTServer.jar"]