INSERT INTO plataforma.grupo (seq_grupo, nom_grupo, tip_grupo) VALUES (1, 'usuario', 'CONFIGURACAO');

INSERT INTO plataforma.papel (seq_papel, nom_papel) VALUES (1, 'peticionador');
INSERT INTO plataforma.papel (seq_papel, nom_papel) VALUES (2, 'recebedor');
INSERT INTO plataforma.papel (seq_papel, nom_papel) VALUES (3, 'representante');
INSERT INTO plataforma.papel (seq_papel, nom_papel) VALUES (4, 'preautuador');
INSERT INTO plataforma.papel (seq_papel, nom_papel) VALUES (5, 'autuador');
INSERT INTO plataforma.papel (seq_papel, nom_papel) VALUES (6, 'distribuidor');
INSERT INTO plataforma.papel (seq_papel, nom_papel) VALUES (7, 'cartoraria');
INSERT INTO plataforma.papel (seq_papel, nom_papel) VALUES (8, 'gestor-autuacao');

INSERT INTO plataforma.usuario (seq_usuario, nom_usuario, sig_usuario, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (plataforma.seq_usuario.nextval, 'peticionador', 'peticionador', '00000000001', 'DF12345', 'petic@teste.com.br', '61-80801212');
INSERT INTO plataforma.usuario (seq_usuario, nom_usuario, sig_usuario, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (plataforma.seq_usuario.nextval, 'recebedor', 'recebedor', '00000000001', null, 'petic@teste.com.br', '61-80801212');
INSERT INTO plataforma.usuario (seq_usuario, nom_usuario, sig_usuario, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (plataforma.seq_usuario.nextval, 'representante', 'representante', '00000000001', 'DF12345', 'petic@teste.com.br', '61-80801212');
INSERT INTO plataforma.usuario (seq_usuario, nom_usuario, sig_usuario, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (plataforma.seq_usuario.nextval, 'preautuador', 'preautuador', '00000000001', null, 'petic@teste.com.br', '61-80801212');
INSERT INTO plataforma.usuario (seq_usuario, nom_usuario, sig_usuario, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (plataforma.seq_usuario.nextval, 'autuador', 'autuador', '00000000001', null, 'petic@teste.com.br', '61-80801212');
INSERT INTO plataforma.usuario (seq_usuario, nom_usuario, sig_usuario, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (plataforma.seq_usuario.nextval, 'distribuidor', 'distribuidor', '00000000001', null, 'petic@teste.com.br', '61-80801212');
INSERT INTO plataforma.usuario (seq_usuario, nom_usuario, sig_usuario, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (plataforma.seq_usuario.nextval, 'cartoraria', 'cartoraria', '00000000001', null, 'petic@teste.com.br', '61-80801212');
INSERT INTO plataforma.usuario (seq_usuario, nom_usuario, sig_usuario, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (plataforma.seq_usuario.nextval, 'gestor-autuacao', 'gestor-autuacao', '00000000001', null, 'petic@teste.com.br', '61-80801212');

INSERT INTO plataforma.grupo_usuario (seq_grupo, seq_usuario) VALUES (1, 1);
INSERT INTO plataforma.grupo_usuario (seq_grupo, seq_usuario) VALUES (1, 2);
INSERT INTO plataforma.grupo_usuario (seq_grupo, seq_usuario) VALUES (1, 3);
INSERT INTO plataforma.grupo_usuario (seq_grupo, seq_usuario) VALUES (1, 4);
INSERT INTO plataforma.grupo_usuario (seq_grupo, seq_usuario) VALUES (1, 5);
INSERT INTO plataforma.grupo_usuario (seq_grupo, seq_usuario) VALUES (1, 6);
INSERT INTO plataforma.grupo_usuario (seq_grupo, seq_usuario) VALUES (1, 7);
INSERT INTO plataforma.grupo_usuario (seq_grupo, seq_usuario) VALUES (1, 8);

INSERT INTO plataforma.papel_usuario (seq_papel, seq_usuario) VALUES (1, 1);
INSERT INTO plataforma.papel_usuario (seq_papel, seq_usuario) VALUES (2, 2);
INSERT INTO plataforma.papel_usuario (seq_papel, seq_usuario) VALUES (3, 3);
INSERT INTO plataforma.papel_usuario (seq_papel, seq_usuario) VALUES (4, 4);
INSERT INTO plataforma.papel_usuario (seq_papel, seq_usuario) VALUES (5, 5);
INSERT INTO plataforma.papel_usuario (seq_papel, seq_usuario) VALUES (6, 6);
INSERT INTO plataforma.papel_usuario (seq_papel, seq_usuario) VALUES (7, 7);
INSERT INTO plataforma.papel_usuario (seq_papel, seq_usuario) VALUES (8, 8);

INSERT INTO plataforma.tipo_informacao (seq_tipo_informacao, nom_tipo_informacao) VALUES (1, 'PETICAO');
INSERT INTO plataforma.tipo_informacao (seq_tipo_informacao, nom_tipo_informacao) VALUES (2, 'PROCESSO');
INSERT INTO plataforma.tipo_informacao (seq_tipo_informacao, nom_tipo_informacao) VALUES (3, 'DASHLET');
INSERT INTO plataforma.tipo_informacao (seq_tipo_informacao, nom_tipo_informacao) VALUES (4, 'NOTIFICACAO');
INSERT INTO plataforma.tipo_informacao (seq_tipo_informacao, nom_tipo_informacao) VALUES (5, 'TAREFA');

INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (1, 'ELETRONICA', 1);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (2, 'FISICA', 1);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (3, 'ORGAO', 1);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (4, 'TAREFA', 3);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (5, 'PETICIONADO', 3);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (6, 'GESTAO', 3);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (7, 'PREAUTUACAO', 5);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (8, 'AUTUACAO', 5);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (9, 'DISTRIBUICAO', 5);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (10, 'DEVOLUCAO', 5);

INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (1, 1, 1, 'CRIAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (2, 1, 2, 'CRIAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (3, 1, 3, 'CRIAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (4, 2, null, 'CRIAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (5, 1, 2, 'ALTERAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (6, 1, null, 'ALTERAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (7, 3, 4, 'VISUALIZAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (8, 3, 5, 'VISUALIZAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (9, 3, 6, 'VISUALIZAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (10, 4, null, 'VISUALIZAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (11, 4, null, 'CRIAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (12, 5, 7, 'EXECUTAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (13, 5, 8, 'EXECUTAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (14, 5, 9, 'EXECUTAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (15, 5, 10, 'EXECUTAR');

INSERT INTO plataforma.permissao_grupo (seq_grupo, seq_permissao) VALUES (1, 10);
INSERT INTO plataforma.permissao_grupo (seq_grupo, seq_permissao) VALUES (1, 11);

INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (1, 1);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (1, 8);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (2, 2);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (2, 8);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (3, 3);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (3, 8);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (4, 7);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (4, 12);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (5, 7);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (5, 13);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (6, 7);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (6, 14);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (7, 7);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (7, 15);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (8, 9);

INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (1, 'REGISTRAR-PETICAO-ELETRONICA', 'ACAO');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (2, 'REGISTRAR-PETICAO-FISICA', 'ACAO');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (3, 'REGISTRAR-PETICAO-ELETRONICA-ORGAO', 'ACAO');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (4, 'PREAUTUAR', 'ACAO');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (5, 'AUTUAR', 'ACAO');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (6, 'DISTRIBUIR-PROCESSO', 'ACAO');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (7, 'DEVOLVER-PETICAO', 'ACAO');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (8, 'MINHAS-TAREFAS', 'DASHLET');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (9, 'MINHAS-PETICOES', 'DASHLET');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (10, 'GRAFICO-GESTAO', 'DASHLET');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (11, 'NOTIFICAR', 'NOTIFICACAO');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (12, 'DO_NOTHING_LONG', 'ACAO');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (13, 'DO_NOTHING', 'ACAO');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (14, 'DUMMY_ACTION', 'ACAO');

INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (1, 1);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (2, 2);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (3, 3);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (4, 12);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (5, 13);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (6, 14);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (7, 15);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (8, 7);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (9, 8);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (10, 9);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (11, 11);
