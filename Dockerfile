FROM openjdk:23-slim
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY build/libs/autonomous-0.0.1-SNAPSHOT.jar autonomous.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "autonomous.jar"]