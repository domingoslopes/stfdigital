delete plataforma.criterio_pesquisa where seq_criterio_pesquisa in (1, 2, 3);

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio, dsc_api, dsc_valor, dsc_descricao)
values (plataforma.seq_criterio_pesquisa.nextval, 'Meio de tramitação', 'meioTramitacao', 'distribuicao', 'SELECAO', '/meiosTramitacao', 'nome', 'descricao');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio, dsc_api, dsc_valor, dsc_descricao)
values (plataforma.seq_criterio_pesquisa.nextval, 'Sigilo', 'sigilo', 'distribuicao', 'SELECAO', '/sigilos', 'nome', 'descricao');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio, dsc_api, dsc_valor, dsc_descricao)
values (plataforma.seq_criterio_pesquisa.nextval, 'Preferências', 'preferencias.codigo', 'distribuicao', 'SELECAO', '/classes/preferencias', 'codigo', 'descricao');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio, dsc_api, dsc_valor, dsc_descricao)
values (plataforma.seq_criterio_pesquisa.nextval, 'Procedência', 'origens.procedenciaGeografica.sequencial', 'distribuicao', 'SELECAO', '/unidades-federacao', 'id', 'sigla');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio, dsc_api, dsc_valor, dsc_descricao)
values (plataforma.seq_criterio_pesquisa.nextval, 'Tribunal ou Juízo de Origem', 'origens.tribunalJuizo.codigo', 'distribuicao', 'SELECAO', '/tribunais-juizos', 'codigo', 'nome');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio)
values (plataforma.seq_criterio_pesquisa.nextval, 'Número do Processo na Origem', 'origens.numeroProcesso', 'distribuicao', 'NUMERO');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio, dsc_api, dsc_valor, dsc_descricao)
values (plataforma.seq_criterio_pesquisa.nextval, 'Classe', 'classe.sigla', 'distribuicao', 'SELECAO', '/classes', 'sigla', 'nome');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio)
values (plataforma.seq_criterio_pesquisa.nextval, 'Número do Processo', 'numero', 'distribuicao', 'NUMERO');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio)
values (plataforma.seq_criterio_pesquisa.nextval, 'Identificação', 'identificacao', 'distribuicao', 'TEXTO');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio, dsc_api, dsc_valor, dsc_descricao)
values (plataforma.seq_criterio_pesquisa.nextval, 'Relator', 'relator.codigo', 'distribuicao', 'SELECAO', '/ministros', 'codigo', 'nome');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio, dsc_api, dsc_valor, dsc_descricao)
values (plataforma.seq_criterio_pesquisa.nextval, 'Parte', 'partes.pessoaId.sequencial', 'distribuicao', 'SELECAO', '/pessoas', 'id', 'nome');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio, dsc_api, dsc_valor, dsc_descricao)
values (plataforma.seq_criterio_pesquisa.nextval, 'Situação', 'situacao', 'distribuicao', 'SELECAO', '/processos/status', 'nome', 'descricao');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio)
values (plataforma.seq_criterio_pesquisa.nextval, 'Data de recebimento', 'dataRecebimento', 'distribuicao', 'DATA');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio)
values (plataforma.seq_criterio_pesquisa.nextval, 'Data de autuação', 'dataAutuacao', 'distribuicao', 'DATA');

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio)
values (plataforma.seq_criterio_pesquisa.nextval, 'Data de distribuição', 'distribuicoes.dataDistribuicao', 'distribuicao', 'DATA');