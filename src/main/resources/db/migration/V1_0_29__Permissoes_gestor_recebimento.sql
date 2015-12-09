INSERT INTO plataforma.papel (seq_papel, nom_papel) VALUES (11, 'gestor-recebimento');

INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (corporativo.seq_pessoa.nextval, 'gestor-recebimento', '00000000001', null, 'petic@teste.com.br', '61-80801212');

INSERT INTO plataforma.usuario (seq_usuario, sig_usuario, seq_pessoa) VALUES (plataforma.seq_usuario.nextval, 'gestor-recebimento', corporativo.seq_pessoa.currval);

INSERT INTO plataforma.grupo_usuario (seq_grupo, seq_usuario) VALUES (1, plataforma.seq_usuario.currval);

INSERT INTO plataforma.papel_usuario (seq_papel, seq_usuario) VALUES (11, plataforma.seq_usuario.currval);

INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (11, 'ASSINATURA', 5);

INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (16, 5, 11, 'EXECUTAR');

INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (11, 16);

INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (11, 7);

INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (15, 'ASSINAR-DEVOLUCAO-PETICAO', 'ACAO');

INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (15, 16);