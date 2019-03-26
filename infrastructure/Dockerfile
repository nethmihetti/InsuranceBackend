FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD /build/libs/insuranceRESTServer-0.1.0.jar insuranceRESTServer.jar
ENTRYPOINT ["java", "-jar", "insuranceRESTServer.jar"]