#!/usr/bin/env bash

docker-machine ssh default "sudo sed -i \"s/EXTRA_ARGS='/EXTRA_ARGS='\n--experimental/\" /var/lib/boot2docker/profile"
echo "Sua m�quina virtual ser� reiniciada"
docker-machine restart default