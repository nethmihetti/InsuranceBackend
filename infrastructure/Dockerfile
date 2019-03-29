FROM openjdk:8-jdk-alpine
EXPOSE 8080

#RUN mkdir -p /usr/src/app
#WORKDIR /usr/src/app

#COPY . /usr/src/app

#RUN ls /home/circleci/RESTserver/build/libs/
#RUN pwd

FROM gradle:4.4.1-jdk9

WORKDIR /app
COPY --from=0 . /app

#USER root                # This changes default user to root
ADD --chown=gradle:gradle . /app
#RUN chown -R gradle /app # This changes ownership of folder
#USER gradle              # This changes the user back to the default user "gradle"

#RUN ./gradlew build --stacktrace
RUN ./gradlew clean dist
RUN ./gradlew build --no-daemon --console plain

RUN ls

RUN ls build/libs

CMD java -jar build/libs/RESTserver-0.0.1-SNAPSHOT.jar