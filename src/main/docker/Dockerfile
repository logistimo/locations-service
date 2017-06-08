FROM openjdk:8-jre

MAINTAINER kumar <kumar@logistimo.com>

VOLUME /temp

ARG APP_NAME
ARG APP_VERSION

EXPOSE 9090

EXPOSE 9099
	
ADD ${APP_NAME}-${APP_VERSION}.jar location-service.jar

RUN bash -c 'touch /location-service.jar'

ENV JAVA_OPTS="" \
	SERVER_PORT=9090 \
	MYSQL_LOGI_HOST=jdbc:mariadb://localhost:3306/logistimo?useUnicode=true&amp;characterEncoding=UTF-8 \
	MYSQL_LOC_HOST=jdbc:mariadb://localhost:3306/locations?useUnicode=true&amp;characterEncoding=UTF-8 \
	MYSQL_LOC_USER=logistimo \
	MYSQL_LOC_PASS=logistimo \
	MYSQL_LOGI_USER=logistimo \
	MYSQL_LOGI_PASS=logistimo \
	REDIS_HOST=localhost \
	JAVA_AGENT_PORT=9099 \
	APP_START=false	

ADD jmx_prometheus_javaagent-0.7.jar /
ADD jmx_exporter.json /

COPY docker-entrypoint.sh /docker-entrypoint.sh

RUN chmod +x /docker-entrypoint.sh

CMD ["/docker-entrypoint.sh"]
