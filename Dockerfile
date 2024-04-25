FROM maven:3-amazoncorretto-17 AS build

WORKDIR /app

COPY . /app

RUN mvn clean package -DskipTests

