FROM java:8

MAINTAINER kumar <kumar@logistimo.com>

VOLUME /temp

ARG APP_NAME
ARG APP_VERSION
	
ADD ${APP_NAME}-${APP_VERSION}.jar location-service.jar

RUN bash -c 'touch /location-service.jar'

ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /location-service.jar" ]