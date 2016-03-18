--Cria uma pessoa.
insert into corporativo.pessoa (seq_pessoa, nom_pessoa, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (corporativo.seq_pessoa.nextval, 'representante-tribunal', '45711386360', null, 'representantetrib@teste.com.br', '61-80901113');

--Cria o usuário.
insert into plataforma.usuario (seq_usuario, sig_usuario, seq_pessoa, cod_setor_lotacao) VALUES (plataforma.seq_usuario.nextval, 'representante-tribunal', corporativo.seq_pessoa.currval, 600000627);
insert into plataforma.grupo_usuario(seq_grupo, seq_usuario) VALUES (1, plataforma.seq_usuario.currval);

--Cria o papel.
insert into plataforma.papel(seq_papel, nom_papel, seq_grupo) VALUES (23, 'representante-tribunal', 2);
insert into plataforma.papel_usuario(seq_papel, seq_usuario) VALUES (23, plataforma.seq_usuario.currval);

--Cria a ação enviar-processo
insert into plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (48, 'enviar-processo', 'ACAO');
insert into plataforma.papel_recurso (seq_papel, seq_recurso) VALUES (23, 48); /* TAREFA ENVIAR-PROCESSO -> PAPEL REPRESENTANTE-TRIBUNAL -> EXECUTAR */