#!/usr/bin/env bash

build_all() {
    projects_install=( "plataforma/core" \
        "plataforma/test"
    )
    
    projects_docker=( "plataforma/discovery" \
        "plataforma/documents" \
        "plataforma/gateway" \
        "plataforma/processos" \
        "plataforma/ui" \
        "plataforma/logging" \
		"plataforma/userauthentication" \
        "autuacao/autuacao" \
        "autuacao/distribuicao" \
        "autuacao/peticionamento" \
        "autuacao/recebimento"
    )

    for project in "${projects_install[@]}"
    do
        cd $project
        echo "Construindo "$project
        gradle install
        cd - > /dev/null
    done

    for project in "${projects_docker[@]}"
    do
        cd $project
        echo "Construindo "$project
        gradle docker
        cd - > /dev/null
    done
}

mkdir -p ./logs
(time build_all) 2>&1 | tee ./logs/build.log