FROM maven:3.6.3-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src

EXPOSE 8080

CMD ["mvn", "spring-boot:run"]
