#!/usr/bin/env bash

projects_ui=("plataforma/core" \
    "plataforma/discovery" \
    "plataforma/documents" \
    "plataforma/gateway" \
    "plataforma/services" \
    "plataforma/test" \
    "plataforma/userauthentication" \
    "autuacao/autuacao" \
    "autuacao/distribuicao" \
    "autuacao/peticionamento" \
    "autuacao/recebimento"
)

command="diffuse "

for project in "${projects_ui[@]}"
do
    command="$command $project/$@"
done

eval $command