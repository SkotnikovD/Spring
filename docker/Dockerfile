FROM openjdk:8-jdk-alpine as builder
WORKDIR /app
COPY ./build.gradle .
COPY ./gradlew .
COPY ./gradle ./gradle
COPY ./settings.gradle .
RUN ./gradlew --no-daemon --version #needs to install gradle
COPY ./src/main ./src/main
RUN ./gradlew --no-daemon bootJar

FROM openjdk:8-jre as runner
WORKDIR /app
COPY --from=0 /app/build/libs/*.jar /app/app.jar
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring
EXPOSE 8080
ENTRYPOINT exec java -XX:+UseG1GC -Xms400m -Xmx400m -jar app.jar