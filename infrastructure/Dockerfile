FROM gradle:4.3-jdk-alpine AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

#COPY build.gradle settings.gradle gradlew $APP_HOME
#COPY gradle $APP_HOME/gradle

COPY . .
RUN ./gradlew build --stacktrace || return 0

FROM gradle:4.3-jdk-alpine
ENV ARTIFACT_NAME=RESTserver-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
EXPOSE 8080
CMD ["java","-jar", "RESTserver-0.0.1-SNAPSHOT.jar"]