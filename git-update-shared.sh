#!/usr/bin/env bash

update_shared_all() {
    projects_shared=("plataforma/documents" \
        "plataforma/processos" \
        "plataforma/ui" \
		"plataforma/identidades" \
		"plataforma/recebimento" \
        "autuacao/autuacao" \
        "autuacao/distribuicao" \
        "autuacao/peticionamento"
    )

    for project in "${projects_shared[@]}"
    do
        cd $project
        echo "Atualizando shared em "$project
        git subrepo pull shared
        cd - > /dev/null
    done
}

update_shared_all