FROM gradle:jdk11 AS build

COPY --chown=gradle:gradle . /app
WORKDIR /app

RUN gradle clean build --no-daemon

FROM openjdk:11 AS deploy

EXPOSE 8081

RUN mkdir /app

COPY --from=build /app/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]
