FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace
COPY shared-libs/events /workspace/shared-libs/events
COPY notification-service /workspace/notification-service
WORKDIR /workspace/notification-service
RUN chmod +x gradlew && ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/notification-service/build/libs/*.jar app.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java", "-jar", "app.jar"]
