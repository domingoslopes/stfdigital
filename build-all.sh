#!/usr/bin/env bash

build_all() {
    projects_install=( "plataforma/core" )
    
    projects_docker=( "plataforma/discovery" \
        "plataforma/gateway" \
        "plataforma/services" \
        "plataforma/ui" \
        "plataforma/documents" \
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

build_all 2>&1 | tee build.log