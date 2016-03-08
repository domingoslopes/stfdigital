-- Cria Dashlet de Modelos e Dashboard Gestor de Modelo
INSERT INTO plataforma.dashboard (seq_dashboard, nom_dashboard) VALUES (plataforma.seq_dashboard.nextval, 'Gestor de Modelo');
INSERT INTO plataforma.dashlet (seq_dashlet, nom_dashlet, dsc_dashlet) VALUES (plataforma.seq_dashlet.nextval, 'modelos', 'Modelos');
INSERT INTO plataforma.dashboard_dashlet (seq_dashboard, seq_dashlet) VALUES (plataforma.seq_dashboard.currval, plataforma.seq_dashlet.currval);

INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (36, 'gestao-modelo', 9);

INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (45, 9, 36, 'VISUALIZAR');

INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (46, 3, 36, 'VISUALIZAR');

INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (37, plataforma.seq_dashboard.currval, 'DASHBOARD');

INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (37, 45);

INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (corporativo.seq_pessoa.nextval, 'gestor de modelo', '02671808453', null, 'gestormodelo@teste.com.br', '61-80801217');
INSERT INTO plataforma.usuario (seq_usuario, sig_usuario, seq_pessoa, cod_setor_lotacao) VALUES (plataforma.seq_usuario.nextval, 'gestor-modelo', corporativo.seq_pessoa.currval, 600000627);

INSERT INTO plataforma.grupo_usuario(seq_grupo, seq_usuario) VALUES (1, plataforma.seq_usuario.currval);

INSERT INTO plataforma.papel(seq_papel, nom_papel, seq_grupo) VALUES (22, 'gestor-modelo', 2);
INSERT INTO plataforma.papel_usuario(seq_papel, seq_usuario) VALUES (22, plataforma.seq_usuario.currval);

INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (corporativo.seq_pessoa.nextval, 'gestor de modelo 01', '02671808453', null, 'gestormodelo01@teste.com.br', '61-80801217');
INSERT INTO plataforma.usuario (seq_usuario, sig_usuario, seq_pessoa, cod_setor_lotacao) VALUES (plataforma.seq_usuario.nextval, 'gestor-modelo-01', corporativo.seq_pessoa.currval, 600000627);
INSERT INTO plataforma.papel_usuario(seq_papel, seq_usuario) VALUES (22, plataforma.seq_usuario.currval);

INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (corporativo.seq_pessoa.nextval, 'gestor de modelo 02', '02671808453', null, 'gestormodelo02@teste.com.br', '61-80801217');
INSERT INTO plataforma.usuario (seq_usuario, sig_usuario, seq_pessoa, cod_setor_lotacao) VALUES (plataforma.seq_usuario.nextval, 'gestor-modelo-02', corporativo.seq_pessoa.currval, 600000627);
INSERT INTO plataforma.papel_usuario(seq_papel, seq_usuario) VALUES (22, plataforma.seq_usuario.currval);

INSERT INTO plataforma.segmento(seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (37, 'CRIACAO-MODELO', 5);
INSERT INTO plataforma.permissao(seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (47, 5, 37, 'EXECUTAR');

INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (45, 22);  /* DASHLET   -> TAREFA                        -> VISUALIZAR */  
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (46, 22); /* DASHBORAD -> GESTOR-MODELO                      -> VISUALIZAR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 22); /* TAREFA    -> CRIAÇÃO DE MODELO -> EXECUTAR   */

INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (38, 'criar-modelo', 'ACAO');
INSERT INTO plataforma.permissao_recurso (seq_permissao, seq_recurso) VALUES (47, 38);