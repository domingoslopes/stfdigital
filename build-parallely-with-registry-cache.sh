#!/usr/bin/env bash

build_parallely() {
    ./scripts/start-npm-proxy.sh
    gradle docker -PnpmRegistry="http://docker:9981/content/groups/npm/"
}

mkdir -p ./support/logs
(time build_parallely) 2>&1 | tee ./support/logs/build-parallely.log