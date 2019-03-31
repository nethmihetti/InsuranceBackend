FROM gradle:4.3-jdk-alpine AS TEMP_BUILD_IMAGE
USER root

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

#RUN addgroup -S appgroup && adduser -S appuser -G appgroup
#USER appuser
#COPY build.gradle settings.gradle gradlew $APP_HOME
#COPY gradle $APP_HOME/gradle

COPY . .

#RUN chown -R admin:appuser $APP_HOME
RUN chmod 755 $APP_HOME
#USER admin

RUN ./gradlew build --stacktrace

FROM gradle:4.3-jdk-alpine
ENV ARTIFACT_NAME=RESTserver-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
EXPOSE 8080


CMD ["java","-jar", "RESTserver-0.0.1-SNAPSHOT.jar"]