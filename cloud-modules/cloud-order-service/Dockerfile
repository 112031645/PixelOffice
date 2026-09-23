FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY cloud-common ./cloud-common
COPY cloud-api ./cloud-api
RUN mvn install -pl cloud-common,cloud-api -am -DskipTests
COPY cloud-order-service ./cloud-order-service
RUN mvn package -pl cloud-order-service -am -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/cloud-order-service/target/cloud-order-service.jar app.jar
ENV TZ=Asia/Shanghai
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "app.jar"]
