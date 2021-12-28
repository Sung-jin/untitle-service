#!/bin/bash

docker stop mysql-demo redis-demo demo-server && docker rm mysql-demo redis-demo demo-server
docker rmi 'demo-server'

script_path=$(dirname "$0")
cd "$script_path" && cd ../

docker-compose -f "$script_path"/docker-compose-infra.yml up -d
# build 시 테스트와 서버에 필요한 환경 구성을 위한 compose

sleep 5s
# mysql, redis 가 올라오기 전에 build 가 시도 되면서 테스트가 실패함.
# TODO - mysql, redis 가 정상적으로 올라오는지 대기하는 로직으로 대체 필요

./gradlew clean build
rm -rf "$script_path"/application.jar
mv build/libs/*.jar "$script_path"/application.jar

cd "$script_path"

docker-compose up -d
