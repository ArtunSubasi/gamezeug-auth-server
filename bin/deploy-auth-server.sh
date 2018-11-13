#!/bin/bash

image=artun/gamezeug-auth-server
containerName=auth-server

printf "\nPulling image $image\n"
docker pull $image

printf "\nStopping container $containerName\n"
docker stop $containerName

printf "\nDeleting container $containerName\n"
docker rm $containerName

printf "\nStarting container $containerName from image $image\n"
docker run -p 8080:8080 -d --name $containerName $image

printf "\nDeployment finished. You are good to go!\n"
