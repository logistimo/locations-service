#!/bin/bash -x


exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom \
    -Dserver.port=$SERVER_PORT \
    -Dspring.logistimo.db.url=$MYSQL_LOGI_HOST \
    -Dspring.location.db.url=$MYSQL_LOC_HOST \
    -Dspring.location.db.username=$MYSQL_LOC_USER \
    -Dspring.location.db.password=$MYSQL_LOC_PASS \
    -Dspring.logistimo.db.username=$MYSQL_LOGI_USER \
    -Dspring.logistimo.db.password=$MYSQL_LOGI_PASS \
    -Dapp.redis.host=$REDIS_HOST \
    -Dapp.redis.port=$REDIS_PORT \
    -Dapp-data.start=$APP_START \
    -Dapp.issentinel=$ISSENTINEL \
    -Dapp.redis.sentinel.master=$SENTINEL_MASTER \
    -Dapp.redis.sentinel.nodes=$SENTINEL_HOST \
    -javaagent:$HOME/jmx_prometheus_javaagent-0.7.jar=$JAVA_AGENT_PORT:$HOME/jmx_exporter.json \
    -jar $HOME/location-service.jar
