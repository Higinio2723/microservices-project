FROM openjdk:17.0.2

WORKDIR /app

COPY ./target/gateway-0.0.1-SNAPSHOT.jar .
ENV EUREKA_SERVER=null

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "gateway-0.0.1-SNAPSHOT.jar"]