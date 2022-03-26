#!/bin/bash

./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8001" &
./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8002" &
./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8003" &