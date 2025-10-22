#Stage 1: build
FROM maven:3.9.11-amazoncorretto-25-alpine AS builder
WORKDIR /build
COPY pom.xml .
COPY src/ ./src/
RUN mvn clean package -DskipTests

#Stage 2: Runtime
FROM eclipse-temurin:24.0.2_12-jre-alpine
WORKDIR /opt/app
COPY --from=builder /build/target/*.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring && \
    chown -R spring:spring /opt/app

# Switch to non-root user
USER spring:spring

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -Dlogging.config=classpath:logback-docker.xml -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
