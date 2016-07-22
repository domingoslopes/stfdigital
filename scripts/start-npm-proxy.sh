#!/usr/bin/env bash

NEXUS_CONTAINER=nexus

EXISTS_NEXUS_CONTAINER=$(docker ps -aq -f name="$NEXUS_CONTAINER")

if [ "$EXISTS_NEXUS_CONTAINER" ]; then
    echo 'Iniciando o container Nexus'
    docker start $NEXUS_CONTAINER
else
    echo 'Criando e iniciando o container Nexus'
    docker create --name $NEXUS_CONTAINER-data sonatype/nexus
    docker run -d -p 9981:8081 --name $NEXUS_CONTAINER --volumes-from $NEXUS_CONTAINER-data sonatype/nexus
fi

echo 'Aguardando o proxy iniciar'
docker run --rm --link $NEXUS_CONTAINER:$NEXUS_CONTAINER aanand/wait

if [ ! "$EXISTS_NEXUS_CONTAINER" ]; then
    echo 'Criando o repositório proxy do https://registry.npmjs.org'
    curl -H "Content-Type:application/json" -d @scripts/nexus-npm-cache.json -X POST -u admin:admin123 http://docker:9981/service/local/repositories
    curl -H "Content-Type:application/json" -d @scripts/nexus-npm-group.json -X POST -u admin:admin123 http://docker:9981/service/local/repo_groups
fi