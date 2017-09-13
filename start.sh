#!/usr/bin/env bash

exec java \
    -XX:NativeMemoryTracking=summary \
    -XshowSettings:vm \
    -Dplay.http.secret.key=${PLAY_HTTP_SECRET_KEY} \
    -jar /app/application.jar
