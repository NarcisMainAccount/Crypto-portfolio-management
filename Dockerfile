FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/Linux-test.jar app.jar

ENTRYPOINT ["java", "- jar", "app.jar"]
