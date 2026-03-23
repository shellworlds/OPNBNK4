FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace
COPY shared-libs/events /workspace/shared-libs/events
COPY transaction-service /workspace/transaction-service
WORKDIR /workspace/transaction-service
RUN chmod +x gradlew && ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/transaction-service/build/libs/*.jar app.jar
EXPOSE 8082
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java", "-jar", "app.jar"]
