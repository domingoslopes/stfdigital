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
    "autuacao/autuacao" \
    "autuacao/distribuicao" \
    "autuacao/peticionamento" \
    "autuacao/recebimento" \
)

for identificador in "${repositorios[@]}"
do
    cd $identificador
    echo "#$identificador"
    ./gradlew eclipse
    echo "#"
    cd - > /dev/null
done
