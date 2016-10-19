#!/usr/bin/env bash


repositorios=( "plataforma/core" \
    "plataforma/documents" \
    "plataforma/discovery" \
    "plataforma/gateway" \
    "plataforma/processos" \
    "plataforma/test" \
    "plataforma/ui" \
    "plataforma/logging" \
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
    git "$@"
    echo "#"
    cd - > /dev/null
done