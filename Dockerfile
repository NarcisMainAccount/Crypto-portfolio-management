FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/linux-test-1.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
