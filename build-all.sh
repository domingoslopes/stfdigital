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
		"plataforma/identidades" \
        "autuacao/autuacao" \
        "autuacao/distribuicao" \
        "autuacao/peticionamento" \
        "autuacao/recebimento"
    )

    for project in "${projects_install[@]}"
    do
        cd $project
        echo "Construindo "$project
        ./gradlew install
        cd - > /dev/null
    done

    for project in "${projects_docker[@]}"
    do
        cd $project
        echo "Construindo "$project
        ./gradlew docker
        cd - > /dev/null
    done
}

mkdir -p ./support/logs
(time build_all) 2>&1 | tee ./support/logs/build.log
