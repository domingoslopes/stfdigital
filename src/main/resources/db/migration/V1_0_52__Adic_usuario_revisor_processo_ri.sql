INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (corporativo.seq_pessoa.nextval, 'revisor de processos ri', '41448296854', null, 'revisori@teste.com.br', '61-80801216');

INSERT INTO plataforma.usuario (seq_usuario, sig_usuario, seq_pessoa, cod_setor_lotacao) VALUES (plataforma.seq_usuario.nextval, 'revisor-processo-ri', corporativo.seq_pessoa.currval, 600000627);

INSERT INTO plataforma.grupo_usuario(seq_grupo, seq_usuario) VALUES (1, plataforma.seq_usuario.currval);

INSERT INTO plataforma.papel(seq_papel, nom_papel, seq_grupo) VALUES (17, 'revisor-processo-recursal-inapto', 2);
INSERT INTO plataforma.papel_usuario(seq_papel, seq_usuario) VALUES (17, plataforma.seq_usuario.currval);

INSERT INTO plataforma.segmento(seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (29, 'REVISAO-PROCESSO-RECURSAL-INAPTO', 5);
INSERT INTO plataforma.permissao(seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (38, 5, 29, 'EXECUTAR');

INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (7, 17);  /* DASHLET   -> TAREFA                             -> VISUALIZAR */  
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (29, 17); /* DASHBORAD -> AUTUACAO                           -> VISUALIZAR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (33, 17); /* TAREFA    -> ASSUMIR                            -> VISUALIZAR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (38, 17); /* TAREFA    -> REVISÃO DE PRCESSO RECURSAL INAPTO -> EXECUTAR   */

INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (32, 'revisar-processo-inapto', 'ACAO');
INSERT INTO plataforma.permissao_recurso (seq_permissao, seq_recurso) VALUES (38, 32);