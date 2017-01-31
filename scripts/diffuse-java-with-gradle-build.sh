#!/usr/bin/env bash

projects_ui=("plataforma/core" \
    "plataforma/discovery" \
    "plataforma/documents" \
    "plataforma/gateway" \
    "plataforma/processos" \
    "plataforma/test" \
    "plataforma/identidades" \
	"plataforma/recebimento" \
    "autuacao/autuacao" \
    "autuacao/distribuicao" \
    "autuacao/peticionamento"
)

command="diffuse "

for project in "${projects_ui[@]}"
do
    command="$command $project/$@"
done

eval $command