FROM eclipse-temurin:24.0.2_12-jre-alpine
WORKDIR /opt/app
COPY target/*.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring && \
    chown -R spring:spring /opt/app

# Switch to non-root user
USER spring:spring

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
