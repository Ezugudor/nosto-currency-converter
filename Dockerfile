FROM maven:3.9.8-sapmachine-21 AS build

WORKDIR /app

COPY .mvn .mvn
COPY mvnw mvnw
RUN chmod +x mvnw

COPY pom.xml .
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw package -DskipTests

FROM openjdk:21-ea-19-slim-buster

WORKDIR /app

COPY --from=build /app/target/nosto-currency-converter-0.0.1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
