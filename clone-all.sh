#!/usr/bin/env bash

repositorios=( "plataforma/core" \
    "plataforma/discovery" \
    "plataforma/documents" \
    "plataforma/gateway" \
    "plataforma/services" \
    "plataforma/test" \
    "plataforma/logging" \
    "plataforma/ui" \
	"plataforma/userauthentication" \
    "autuacao/autuacao" \
    "autuacao/distribuicao" \
    "autuacao/peticionamento" \
    "autuacao/recebimento" \
)

for identificador in "${repositorios[@]}"
do
    repositorio="stfdigital-"${identificador/\//\-}
    if [ ! -d $identificador"/.git" ]; then
        git clone "https://github.com/supremotribunalfederal/"$repositorio $identificador
    fi
done