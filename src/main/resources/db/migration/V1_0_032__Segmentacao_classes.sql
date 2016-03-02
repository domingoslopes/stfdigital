insert into plataforma.tipo_informacao (seq_tipo_informacao, nom_tipo_informacao) values (7, 'CLASSE');
insert into plataforma.segmento (seq_segmento, seq_tipo_informacao, nom_segmento) values (12, 7, 'CIDADAO');
insert into plataforma.segmento (seq_segmento, seq_tipo_informacao, nom_segmento) values (13, 7, 'ADVOGADO');
insert into plataforma.segmento (seq_segmento, seq_tipo_informacao, nom_segmento) values (14, 7, 'INTERNO');

insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 12, 'HC');

insert into plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) values (18, 7, 12, 'VISUALIZAR');
insert into plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) values (19, 7, 12, 'PESQUISAR');

insert into plataforma.papel (seq_papel, nom_papel) values (12, 'cidadao');

insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (1, 12);
insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (8, 12);
insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (18, 12);
insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (19, 12);

INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa, cod_cpf, cod_oab, dsc_email, dsc_telefone) VALUES (corporativo.seq_pessoa.nextval, 'cidadao', '00000000001', null, 'petic@teste.com.br', '61-80801212');

INSERT INTO plataforma.usuario (seq_usuario, sig_usuario, seq_pessoa) VALUES (plataforma.seq_usuario.nextval, 'cidadao', corporativo.seq_pessoa.currval);

INSERT INTO plataforma.grupo_usuario (seq_grupo, seq_usuario) VALUES (1, plataforma.seq_usuario.currval);

insert into plataforma.papel_usuario (seq_papel, seq_usuario) values (12, plataforma.seq_usuario.currval);

insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'AC');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'ACO');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'ADC');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'ADI');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'ADO');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'ADPF');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'AImp');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'AO');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'AOE');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'AR');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'AS');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'CC');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'EI');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'EL');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'HC');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'HD');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'IF');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'MI');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'MS');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'PSV');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'Pet');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'Rcl');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'RvC');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'SL');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'SS');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 13, 'STA');

insert into plataforma.papel (seq_papel, nom_papel) values (13, 'advogado');

insert into plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) values (20, 7, 13, 'VISUALIZAR');
insert into plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) values (21, 7, 13, 'PESQUISAR');

insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (8, 13);
insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (20, 13);
insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (21, 13);

insert into plataforma.papel_usuario (seq_papel, seq_usuario) values (13, 1);
insert into plataforma.papel_usuario (seq_papel, seq_usuario) values (13, 3);

insert into plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) values (22, 7, 14, 'VISUALIZAR');
insert into plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) values (23, 7, 14, 'PESQUISAR');

insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (22, 4);
insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (22, 5);
insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (22, 6);
insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (22, 8);
insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (22, 11);
insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (23, 4);
insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (23, 5);
insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (23, 6);
insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (23, 8);
insert into plataforma.permissao_papel (seq_permissao, seq_papel) values (23, 11);

insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'AC');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'ACO');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'ADC');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'ADI');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'ADO');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'ADPF');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'AI');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'AImp');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'AO');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'AOE');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'AP');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'AR');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'ARE');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'AS');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'CC');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'Cm');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'EI');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'EL');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'EP');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'Ext');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'HC');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'HD');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'IF');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'Inq');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'MI');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'MS');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'OACO');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'PPE');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'PSV');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'Pet');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'RC');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'RE');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'RHC');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'RHD');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'RMI');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'RMS');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'Rcl');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'RvC');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'SEC');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'SL');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'SS');
insert into plataforma.informacao (seq_informacao, seq_tipo_informacao, seq_segmento, cod_identidade) values (plataforma.seq_tipo_informacao.nextval, 7, 14, 'STA');