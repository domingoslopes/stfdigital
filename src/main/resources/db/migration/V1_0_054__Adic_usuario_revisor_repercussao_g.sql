INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (corporativo.seq_pessoa.nextval, 'revisor de repercussao geral', '64724775157', null, 'revisorrg@teste.com.br', '61-80801218');

INSERT INTO plataforma.usuario (seq_usuario, sig_usuario, seq_pessoa, cod_setor_lotacao) VALUES (plataforma.seq_usuario.nextval, 'revisor-repercussao-g', corporativo.seq_pessoa.currval, 600000627);

INSERT INTO plataforma.grupo_usuario(seq_grupo, seq_usuario) VALUES (1, plataforma.seq_usuario.currval);

INSERT INTO plataforma.papel(seq_papel, nom_papel, seq_grupo) VALUES (19, 'revisor-repercussao-geral', 2);
INSERT INTO plataforma.papel_usuario(seq_papel, seq_usuario) VALUES (19, plataforma.seq_usuario.currval);

INSERT INTO plataforma.segmento(seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (31, 'REVISAO-REPERCUSSAO-GERAL', 5);
INSERT INTO plataforma.permissao(seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (40, 5, 31, 'EXECUTAR');

INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (7, 19);  /* DASHLET   -> TAREFA                       -> VISUALIZAR */  
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (29, 19); /* DASHBORAD -> AUTUACAO                     -> VISUALIZAR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (33, 19); /* TAREFA    -> ASSUMIR                      -> VISUALIZAR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (40, 19); /* TAREFA    -> REVISÃO DE REPERCUSSÃO GERAL -> EXECUTAR   */

INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (34, 'revisar-repercussao-geral', 'ACAO');
INSERT INTO plataforma.permissao_recurso (seq_permissao, seq_recurso) VALUES (40, 34);