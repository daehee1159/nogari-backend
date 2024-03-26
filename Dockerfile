FROM openjdk:8-jre
COPY build/libs/nogari-0.0.1-SNAPSHOT.jar service.jar
ENTRYPOINT ["java", "-jar", "service.jar"]
ENV TZ Asia/Seoul
