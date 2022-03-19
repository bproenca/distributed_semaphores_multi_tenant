#!/bin/bash
curl --location --request POST 'localhost:8001/consume?threads=1'
curl --location --request POST 'localhost:8002/consume?threads=2'
curl --location --request POST 'localhost:8003/consume?threads=2'