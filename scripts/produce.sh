#!/bin/bash

curl 'localhost:8001/ping'
curl 'localhost:8002/ping'
curl 'localhost:8003/ping'

curl --location --request POST 'localhost:8001/config'
curl --location --request POST 'localhost:8001/produce'

