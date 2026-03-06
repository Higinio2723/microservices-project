FROM openjdk:17.0.2

WORKDIR /app

COPY ./target/msvcemployee-0.0.1-SNAPSHOT.jar .
ENV EUREKA_URL null

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "msvcemployee-0.0.1-SNAPSHOT.jar"]
