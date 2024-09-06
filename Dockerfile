FROM eclipse-temurin:17
COPY target/devops.jar devops.jar
ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", "-jar", "devops.jar"]