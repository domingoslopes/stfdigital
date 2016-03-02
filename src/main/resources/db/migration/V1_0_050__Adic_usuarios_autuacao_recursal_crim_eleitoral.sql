INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (corporativo.seq_pessoa.nextval, 'autuador recursal crim eleitoral', '55804443038', null, 'prearce@teste.com.br', '61-80801214');

INSERT INTO plataforma.usuario (seq_usuario, sig_usuario, seq_pessoa, cod_setor_lotacao) VALUES (plataforma.seq_usuario.nextval, 'autuador-recursal-ce', corporativo.seq_pessoa.currval, 600000627);

INSERT INTO plataforma.grupo_usuario(seq_grupo, seq_usuario) VALUES (1, plataforma.seq_usuario.currval);

INSERT INTO plataforma.papel(seq_papel, nom_papel, seq_grupo) VALUES (15, 'autuador-recursal-criminal-eleitoral', 2);
INSERT INTO plataforma.papel_usuario(seq_papel, seq_usuario) VALUES (15, plataforma.seq_usuario.currval);

INSERT INTO plataforma.segmento(seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (27, 'PREAUTUACAO-RECURSAL-CRIMINAL-ELEITORAL', 5);
INSERT INTO plataforma.permissao(seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (36, 5, 27, 'EXECUTAR');

INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (7, 15);  /* DASHLET   -> TAREFA                                -> VISUALIZAR */  
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (22, 15); /* CLASSE    -> INTERNO                               -> VISUALIZAR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (23, 15); /* CLASSE    -> INTERNO                               -> PESQUISAR  */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (29, 15); /* DASHBORAD -> AUTUACAO                              -> VISUALIZAR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (33, 15); /* TAREFA    -> ASSUMIR                               -> VISUALIZAR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (36, 15); /* TAREFA    -> PREAUTUAR-RECURSAL-CRIMINAL-ELEITORAL -> EXECUTAR   */

INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (30, 'autuar-recursal-criminal-eleitoral', 'ACAO');
INSERT INTO plataforma.permissao_recurso (seq_permissao, seq_recurso) VALUES (36, 30);