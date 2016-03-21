insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio, dsc_api, dsc_valor, dsc_descricao)
values (plataforma.seq_criterio_pesquisa.nextval, 'Meio de tramitação', 'meioTramitacao', 'autuacao', 'SELECAO', '/meiosTramitacao', 'nome', 'descricao');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio)
values (plataforma.seq_criterio_pesquisa.nextval, 'Ano', 'ano', 'autuacao', 'NUMERO');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio)
values (plataforma.seq_criterio_pesquisa.nextval, 'Número', 'numero', 'autuacao', 'NUMERO');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio)
values (plataforma.seq_criterio_pesquisa.nextval, 'Identificação', 'identificacao', 'autuacao', 'TEXTO');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio, dsc_api, dsc_valor, dsc_descricao)
values (plataforma.seq_criterio_pesquisa.nextval, 'Parte', 'partes.pessoaId.sequencial', 'autuacao', 'SELECAO', '/pessoas', 'id', 'nome');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio, dsc_api, dsc_valor, dsc_descricao)
values (plataforma.seq_criterio_pesquisa.nextval, 'Situação', 'processosWorkflow.status', 'autuacao', 'SELECAO', '/peticoes/status', 'nome', 'descricao');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio)
values (plataforma.seq_criterio_pesquisa.nextval, 'Data de recebimento', 'dataCadastramento', 'autuacao', 'DATA');