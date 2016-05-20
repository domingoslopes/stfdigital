#!/usr/bin/env bash

repositorios=( "discovery" \
    "gateway" \
    "services" \
    "ui" \
    "documents" \
    "autuacao" \
    "distribuicao" \
    "peticionamento" \
    "recebimento" \
)

for identificador in "${repositorios[@]}"
do
    (docker stop $identificador && docker rm $identificador) || true
done