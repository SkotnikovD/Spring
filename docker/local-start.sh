#!/bin/bash
SCRIPT_DIR=$(readlink -f $(dirname "$0"))
ROOT_DIR=$(dirname ${SCRIPT_DIR})

docker build -t spr -f ${SCRIPT_DIR}/Dockerfile ${ROOT_DIR}
docker run -it -d --rm --name springlearn-be --env SPRING_PROFILES_ACTIVE=local --publish=8080:8080 spr