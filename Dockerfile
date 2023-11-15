FROM eclipse-temurin:21-jre-alpine
WORKDIR /opt
ARG jar
COPY $jar .
ENTRYPOINT exec java -Xms100M -Xmx100M -jar *.jar
