FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN mvn clean package -DskipTests && \
    JAR_FILE=$(find target -maxdepth 1 -name "*.jar" ! -name "original-*.jar" | head -n 1) && \
    cp "$JAR_FILE" app.jar

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
