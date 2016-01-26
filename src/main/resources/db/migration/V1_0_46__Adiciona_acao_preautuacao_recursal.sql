INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (corporativo.seq_pessoa.nextval, 'preautuador-recursal', '14967341497', null, 'par@teste.com.br', '61-80801212');

INSERT INTO plataforma.usuario (seq_usuario, sig_usuario, seq_pessoa, cod_setor_lotacao) VALUES (plataforma.seq_usuario.nextval, 'preautuador-recursal', corporativo.seq_pessoa.currval, 600000627);

INSERT INTO plataforma.grupo_usuario(seq_grupo, seq_usuario) VALUES (1, plataforma.seq_usuario.currval);

INSERT INTO plataforma.papel(seq_papel, nom_papel, seq_grupo) VALUES (14, 'preautuador-recursal', 2);

INSERT INTO plataforma.papel_usuario(seq_papel, seq_usuario) VALUES (14, plataforma.seq_usuario.currval);

INSERT INTO plataforma.segmento(seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (26, 'PREAUTUACAO-RECURSAL', 5);
INSERT INTO plataforma.permissao(seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (35, 5, 26, 'EXECUTAR');

INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (7, 14);  /* DASHLET   -> TAREFA               -> VISUALIZAR */  
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (22, 14); /* CLASSE    -> INTERNO              -> VISUALIZAR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (23, 14); /* CLASSE    -> INTERNO              -> PESQUISAR  */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (29, 14); /* DASHBORAD -> AUTUACAO             -> VISUALIZAR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (33, 14); /* TAREFA    -> ASSUMIR              -> VISUALIZAR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (35, 14); /* TAREFA    -> PREAUTUAR-RECURSAL -> EXECUTAR   */

INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (29, 'preautuar-recursal', 'ACAO');
INSERT INTO plataforma.permissao_recurso (seq_permissao, seq_recurso) VALUES (35, 29);
