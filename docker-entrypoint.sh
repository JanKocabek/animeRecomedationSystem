#!/bin/sh
exec java \
    -XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -XX:InitialRAMPercentage=50.0 \
    -XX:+UseG1GC \
    -XX:MaxGCPauseMillis=100 \
    -XX:+UseStringDeduplication \
    -Dlogging.config=classpath:logback-docker.xml \
    -Djava.security.egd=file:/dev/./urandom \
    $JAVA_OPTS \
    -Dserver.port=${PORT:-8080} \
    -jar app.jar