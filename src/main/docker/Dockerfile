FROM openjdk:8-jre

MAINTAINER dockers@logistimo.com


ENV HOME /root

ARG APP_NAME
ARG APP_VERSION

ADD ${APP_NAME}-${APP_VERSION}.jar $HOME/location-service.jar

RUN bash -c 'touch $HOME/location-service.jar'

ENV JAVA_OPTS="-Xms512M -Xmx1G" \
	SERVER_PORT=9090 \
	MYSQL_LOGI_HOST=jdbc:mariadb://localhost:3306/logistimo?useUnicode=true&amp;characterEncoding=UTF-8 \
	MYSQL_LOC_HOST=jdbc:mariadb://localhost:3306/locations?useUnicode=true&amp;characterEncoding=UTF-8 \
	MYSQL_LOC_USER=logistimo \
	MYSQL_LOC_PASS=logistimo \
	MYSQL_LOGI_USER=logistimo \
	MYSQL_LOGI_PASS=logistimo \
	REDIS_HOST=localhost \
	REDIS_PORT=6379 \
	SENTINEL_HOST=localhost:26379,localhost:26378 \
	SENTINEL_MASTER=mymaster \
	ISSENTINEL=false \
	JAVA_AGENT_PORT=9099 \
	SERVICE_NAME=logi-locations \
    APM_SERVER_URL=localhost:8200 \
    APM_AGENT_VER=0.6.0
	APP_START=false

RUN wget -P $HOME/ http://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.7/jmx_prometheus_javaagent-0.7.jar

ADD jmx_exporter.json $HOME/

COPY docker-entrypoint.sh /docker-entrypoint.sh

RUN chmod +x /docker-entrypoint.sh

EXPOSE 9090 9099

CMD ["/docker-entrypoint.sh"]
