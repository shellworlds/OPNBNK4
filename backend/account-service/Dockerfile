# Build context must be ./backend (see docker-compose) so composite includeBuild("../shared-libs/events") resolves.
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace
COPY shared-libs/events /workspace/shared-libs/events
COPY account-service /workspace/account-service
WORKDIR /workspace/account-service
RUN chmod +x gradlew && ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/account-service/build/libs/*.jar app.jar
EXPOSE 8081
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java", "-jar", "app.jar"]
