# 多阶段构建
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY cloud-common ./cloud-common
COPY cloud-api ./cloud-api
RUN mvn install -pl cloud-common,cloud-api -am -DskipTests
COPY cloud-user-service ./cloud-user-service
RUN mvn package -pl cloud-user-service -am -DskipTests

# 运行阶段
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/cloud-user-service/target/cloud-user-service.jar app.jar
ENV TZ=Asia/Shanghai
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "app.jar"]
