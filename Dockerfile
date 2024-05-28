FROM maven:3-openjdk-17 as builder
LABEL authors="rafael"

WORKDIR /build
COPY . /build

RUN mvn clean package -DskipTests -Dcheckstyle.skip

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /build/target/*.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]
