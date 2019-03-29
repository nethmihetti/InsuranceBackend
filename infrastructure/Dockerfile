FROM gradle:4.3-jdk-alpine
EXPOSE 8080

#RUN mkdir -p /usr/src/app
#WORKDIR /usr/src/app

#COPY . /usr/src/app

#RUN ls /home/circleci/RESTserver/build/libs/
#RUN pwd




COPY . /app

#USER root                # This changes default user to root
ADD --chown=gradle . /app
WORKDIR /app
#RUN chown -R gradle /app # This changes ownership of folder
#USER gradle              # This changes the user back to the default user "gradle"

RUN gradle build --stacktrace
#RUN ./gradlew clean dist
#CMD ["./gradlew", "--stacktrace", "build"]

RUN ls

RUN ls build/libs

CMD java -jar build/libs/RESTserver-0.0.1-SNAPSHOT.jar