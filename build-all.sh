#!/usr/bin/env bash

cd plataforma/core;            gradle install; cd -

cd autuacao/peticionamento;    gradle docker; cd -
cd autuacao/recebimento;       gradle docker; cd -
cd autuacao/autuacao;          gradle docker; cd -
cd autuacao/distribuicao;      gradle docker; cd -

cd plataforma/discovery;       gradle docker; cd -
cd plataforma/gateway;         gradle docker; cd -
cd plataforma/services;        gradle docker; cd -
cd plataforma/documents;        gradle docker; cd -

