FROM openjdk:24-jdk-slim
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY target/animeRecomedationSystem-0.0.1-SNAPSHOT.jar animerecomedationsystem.jar
EXPOSE 8080
#ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar animerecomedationsystem.jar"]
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar animerecomedationsystem.jar"]
