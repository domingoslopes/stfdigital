INSERT INTO plataforma.papel (seq_papel, nom_papel) VALUES (9, 'gestor-orgao');
INSERT INTO plataforma.papel (seq_papel, nom_papel) VALUES (10, 'gestor-cadastro');

INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (9, 3);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (9, 8);

INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa, cod_cpf, dsc_email, dsc_telefone) VALUES (corporativo.seq_pessoa.nextval, 'gestor-orgao', '00000000001', 'petic@teste.com.br', '61-80801212');

insert into corporativo.associado (seq_associado, seq_pessoa, seq_orgao, tip_associado, dsc_cargo_funcao)
values (corporativo.seq_associado.nextval, corporativo.seq_pessoa.currval, 6, 'GESTOR', 'Gestor do órgão');

INSERT INTO plataforma.usuario (seq_usuario, sig_usuario, seq_pessoa) VALUES (plataforma.seq_usuario.nextval, 'gestor-orgao', corporativo.seq_pessoa.currval);

INSERT INTO plataforma.grupo_usuario (seq_grupo, seq_usuario) VALUES (1, plataforma.seq_usuario.currval);

INSERT INTO plataforma.papel_usuario (seq_papel, seq_usuario) VALUES (9, plataforma.seq_usuario.currval);

INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa, cod_cpf, dsc_email, dsc_telefone) VALUES (corporativo.seq_pessoa.nextval, 'gestor-cadastro', '00000000001', 'petic@teste.com.br', '61-80801212');

INSERT INTO plataforma.usuario (seq_usuario, sig_usuario, seq_pessoa) VALUES (plataforma.seq_usuario.nextval, 'gestor-cadastro', corporativo.seq_pessoa.currval);

INSERT INTO plataforma.grupo_usuario (seq_grupo, seq_usuario) VALUES (1, plataforma.seq_usuario.currval);

INSERT INTO plataforma.papel_usuario (seq_papel, seq_usuario) VALUES (10, plataforma.seq_usuario.currval);


insert into corporativo.associado (seq_associado, seq_pessoa, seq_orgao, tip_associado, dsc_cargo_funcao)
values (corporativo.seq_associado.nextval, 18, 6, 'REPRESENTANTE', 'Representante');


INSERT INTO plataforma.tipo_informacao (seq_tipo_informacao, nom_tipo_informacao) VALUES (6, 'ASSOCIADO');

INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (17, 6, null, 'CRIAR');

INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (16, 'REGISTRAR-ASSOCIADO', 'ACAO');
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (16, 17);

INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (9, 17);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (10, 17);