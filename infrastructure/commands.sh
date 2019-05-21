#!/usr/bin/env bash

git clone -b authentication git@github.com:nethmihetti/InsuranceBackend.git --depth=1

cd RESTserver

docker build --no-cache -f infrastructure/Dockerfile -t resetserverbackend .

docker run -d -e DATASOURCE_URL="jdbc:postgresql://soramitsuipbackend.ccoyhnadmdam.us-east-2.rds.amazonaws.com:5432/insuranceAggregator?currentSchema=main" \
-e DATASOURCE_USERNAME="insuranceAdmin" \
-e DATASOURCE_PASSWORD="soramitsu1234" \
-p 8080:8080 resetserverbackend:latest