#!/bin/bash
image_name=$1
container_name=$image_name
tag=$2
jar_name=$3
yml_name=$4

echo "Starting deploy -----------"
echo "Stopping container......"
if [ $(docker ps -aq --filter name=$container_name) ];
        then docker stop $container_name;fi
echo "Remove container........."
if [ $(docker ps -aq --filter name=$container_name) ];
        then docker rm -f $container_name;fi
echo "Remove container success..........."
echo "Remove image........."
if [[ "$(docker images -q $image_name] 2> /dev/null)" != "" ]];
        then docker rmi $image_name;fi
echo "Remove image success..............."
echo "Start building image......................"
docker build --build-arg JAR_NAME=$jar_name -f dockerfile -t $image_name:$tag .
echo "Build image success....................."
echo "Starting................................."
docker-compose -f $yml_name up -d
echo "Container start success"

