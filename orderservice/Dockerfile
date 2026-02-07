FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar

RUN addgroup -S app && adduser -S app -G app
USER app

EXPOSE 8082

ENTRYPOINT ["java","-jar", "app.jar"]
