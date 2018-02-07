FROM java:8-jdk-alpine
MAINTAINER Pablo Rey <prperiscal@gmail.com>

ENV JAVA_TOOL_OPTIONS=""

ARG JAR_FILE
# Add the service itself
COPY target/${JAR_FILE} /service.jar

ENTRYPOINT ["java", "-jar", "/service.jar"]
