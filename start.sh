#!/usr/bin/env bash

exec java \
    -XX:+UnlockExperimentalVMOptions \
    -XX:+UseCGroupMemoryLimitForHeap \
    -XX:NativeMemoryTracking=summary \
    -XshowSettings:vm \
    -Dplay.http.secret.key=${PLAY_HTTP_SECRET_KEY} \
    -jar /app/assembly.jar
