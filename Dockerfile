#DockerFile Setup
FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/ajo-app.jar ajo-app.jar
ENTRYPOINT ["java", "-jar", "ajo-app.jar"]