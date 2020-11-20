# DEVELOPING

The application must run in the docker container.

## Build 

mvn -P docker clean install

## Update application in container

A simple way to replace the application in the container without build a new image.

server/updateApp.sh
 

## Run the container for remote debugging

The port 5005 is used for remote debugging from the host.
The port 64014 is used for Rest request from the host.

docker run -i --rm -p 64014:8080 -p 5005:5005 -e JAVA_ENABLE_DEBUG="true" --name apk quarkus/server-alpine-jvm

## Test the Rest with Curl
docker run -i --rm -p 64014:8080 --name apk quarkus/server-alpine-jvm

Create a collection. The first time it will be collection '1'. 
curl -v -w "\n" -X POST  http://localhost:64014/v1/packages

In created collection 1 add "item1"
curl -v -w "\n" -X PUT  -H "Content-Type: text/plain"  http://localhost:64014/v1/packages/1/name -d 'item1' 

Get collection 1
curl -v -w "\n"  -H "Content-Type: application/json"  http://localhost:64014/v1/packages/1 

Get all collections 
curl -v -w "\n" -H "Content-Type: application/json"  http://localhost:64014/v1/packages 

********************
