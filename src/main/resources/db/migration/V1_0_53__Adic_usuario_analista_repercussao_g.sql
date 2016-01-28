INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (corporativo.seq_pessoa.nextval, 'analista de repercussao geral', '02671808453', null, 'analistarg@teste.com.br', '61-80801217');

INSERT INTO plataforma.usuario (seq_usuario, sig_usuario, seq_pessoa, cod_setor_lotacao) VALUES (plataforma.seq_usuario.nextval, 'analista-repercussao-g', corporativo.seq_pessoa.currval, 600000627);

INSERT INTO plataforma.grupo_usuario(seq_grupo, seq_usuario) VALUES (1, plataforma.seq_usuario.currval);

INSERT INTO plataforma.papel(seq_papel, nom_papel, seq_grupo) VALUES (18, 'analista-repercussao-geral', 2);
INSERT INTO plataforma.papel_usuario(seq_papel, seq_usuario) VALUES (18, plataforma.seq_usuario.currval);

INSERT INTO plataforma.segmento(seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (30, 'ANALISE-REPERCUSSAO-GERAL', 5);
INSERT INTO plataforma.permissao(seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (39, 5, 30, 'EXECUTAR');

INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (7, 18);  /* DASHLET   -> TAREFA                       -> VISUALIZAR */  
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (29, 18); /* DASHBORAD -> AUTUACAO                     -> VISUALIZAR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (33, 18); /* TAREFA    -> ASSUMIR                      -> VISUALIZAR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (39, 18); /* TAREFA    -> ANÁLISE DE REPERCISSÃO GERAL -> EXECUTAR   */

INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (33, 'analisar-repercussao-geral', 'ACAO');
INSERT INTO plataforma.permissao_recurso (seq_permissao, seq_recurso) VALUES (39, 33);