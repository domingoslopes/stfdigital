#!/usr/bin/env bash


repositorios=( "plataforma/core" \
    "plataforma/documents" \
    "plataforma/discovery" \
    "plataforma/gateway" \
    "plataforma/services" \
    "plataforma/test" \
    "plataforma/ui" \
    "plataforma/userauthentication" \
    "autuacao/autuacao" \
    "autuacao/distribuicao" \
    "autuacao/peticionamento" \
    "autuacao/recebimento" \
)

for identificador in "${repositorios[@]}"
do
    cd $identificador
    echo "#$identificador"
    git "$@"
    echo "#"
    cd - > /dev/null
done