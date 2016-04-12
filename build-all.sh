#!/usr/bin/env bash

cd plataforma/core;            gradle install; cd -

cd autuacao/peticionamento;    gradle build buildDocker; cd -
cd autuacao/recebimento;       gradle build buildDocker; cd -
cd autuacao/autuacao;          gradle build buildDocker; cd -
cd autuacao/distribuicao;      gradle build buildDocker; cd -

cd plataforma/discovery;       gradle build buildDocker; cd -
cd plataforma/gateway;         gradle build buildDocker; cd -
cd plataforma/services;        gradle build buildDocker; cd -

