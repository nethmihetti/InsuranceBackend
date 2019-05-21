#!/usr/bin/env bash

ssh root@10.90.137.18 -o StrictHostKeyChecking=no
mkdir newfolder
#ssh -vvv root@${SSH_HOST} 'docker-compose -f infrastructure/docker-compose.yml up -d'
