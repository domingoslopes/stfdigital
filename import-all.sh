#!/usr/bin/env bash

repositorios=(
    "plataforma/core" \
    "plataforma/discovery" \
    "plataforma/documents" \
    "plataforma/gateway" \
    "plataforma/processos" \
    "plataforma/test" \
    "plataforma/ui" \
    "plataforma/identidades" \
	"plataforma/recebimento" \
    "autuacao/autuacao" \
    "autuacao/distribuicao" \
    "autuacao/peticionamento"
)

for identificador in "${repositorios[@]}"
do
    cd $identificador
    echo "#$identificador"
    ./gradlew eclipse
    echo "#"
    cd - > /dev/null
done
