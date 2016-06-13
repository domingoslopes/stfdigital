#!/usr/bin/env bash

repositorios=( "plataforma/core" \
    "plataforma/discovery" \
    "plataforma/gateway" \
    "plataforma/services" \
    "plataforma/ui" \
    "plataforma/documents" \
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