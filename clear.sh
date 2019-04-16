#!/bin/bash

echo Entro al Script
if [[ -n `docker ps -a | grep portafolioDev` ]]; then\
docker stop portafolioDev && docker rm portafolioDev;\
fi