FROM maven:3.6.3-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src

CMD ["mvn", "spring-boot:run"]
