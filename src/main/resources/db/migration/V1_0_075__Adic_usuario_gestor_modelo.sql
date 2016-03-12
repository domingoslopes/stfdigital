-- Cria Dashlet de Modelos e Dashboard Gestor de Modelo
INSERT INTO plataforma.dashboard (seq_dashboard, nom_dashboard) VALUES (plataforma.seq_dashboard.nextval, 'Gestor de Modelo');
INSERT INTO plataforma.dashlet (seq_dashlet, nom_dashlet, dsc_dashlet) VALUES (plataforma.seq_dashlet.nextval, 'modelos', 'Modelos');
INSERT INTO plataforma.dashboard_dashlet (seq_dashboard, seq_dashlet) VALUES (plataforma.seq_dashboard.currval, plataforma.seq_dashlet.currval);

INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (44, plataforma.seq_dashboard.currval, 'DASHBOARD');

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

INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (45, 'criar-modelo', 'ACAO');

INSERT INTO plataforma.papel_recurso (seq_papel, seq_recurso) VALUES (22, 44); /* DASHBORAD   -> GESTOR-MODELO   -> VISUALIZAR */
INSERT INTO plataforma.papel_recurso (seq_papel, seq_recurso) VALUES (22, 45); /* TAREFA    -> CRIAÇÃO DE MODELO -> EXECUTAR   */


