#!/usr/bin/env bash

exec java \
    -Dplay.http.secret.key=${PLAY_HTTP_SECRET_KEY} \
    -jar /app/application.jar
