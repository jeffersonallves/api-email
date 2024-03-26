FROM maven:3.9.3-eclipse-temurin-17-focal AS build
WORKDIR /app
COPY . .
RUN mvn clean package -X

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build ./app/target/*.war ./api-email.war
ENTRYPOINT java -jar api-email.war