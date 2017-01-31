#!/usr/bin/env bash

repositorios=( "plataforma/core" \
    "plataforma/discovery" \
    "plataforma/documents" \
    "plataforma/gateway" \
    "plataforma/processos" \
    "plataforma/test" \
    "plataforma/ui" \
    "plataforma/logging" \
	"plataforma/identidades" \
	"plataforma/recebimento" \
    "autuacao/autuacao" \
    "autuacao/distribuicao" \
    "autuacao/peticionamento"
)

for identificador in "${repositorios[@]}"
do
    if [ -d $identificador"/.git" ]; then
        cd $identificador
        echo "Fazendo pull de "$identificador
        git pull
        cd - > /dev/null
    fi
done