FROM openjdk:8-jdk-alpine
EXPOSE 8080

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY . /usr/src/app

RUN ls /
RUN ls /build
RUN ls /build/libs
RUN pwd

#COPY /build/libs/insuranceRESTServer-0.1.0.jar insuranceRESTServer.jar
#ENTRYPOINT ["java","-jar","/usr/src/app/build/libs/RESTserver-0.0.1-SNAPSHOT.jar"]

CMD java -jar RESTserver-0.0.1-SNAPSHOT.jar