#!/bin/sh

curl -i \
-X POST \
-H "Content-Type: application/json" \
-d  '{}' \
http://localhost:8080/fims2/v2/mediaAMEService/job

