FROM eclipse-temurin:17-jre

RUN useradd -ms /bin/bash appuser
USER appuser

WORKDIR /home/appuser
COPY --chown=appuser target/training-portal-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
