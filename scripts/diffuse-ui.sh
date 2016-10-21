#!/usr/bin/env bash

projects_ui=("plataforma/documents" \
    "plataforma/processos" \
    "plataforma/identidades" \
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