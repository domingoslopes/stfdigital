create table corporativo.setor (cod_setor bigint not null, nom_setor varchar2(100) not null, sig_setor varchar2(30) not null, constraint pk_setor primary key (cod_setor));
alter table corporativo.setor add constraint uk_nom_setor_seto unique(nom_setor);
alter table corporativo.setor add constraint uk_sig_setor_seto unique(sig_setor);

alter table plataforma.usuario add column cod_setor_lotacao bigint;
alter table plataforma.usuario add constraint fk_setor_usua foreign key (cod_setor_lotacao) references corporativo.setor;

insert into corporativo.setor (cod_setor, nom_setor, sig_setor) values (600000627, 'SECRETARIA JUDICIÁRIA', 'SEJ');
insert into corporativo.setor (cod_setor, nom_setor, sig_setor) values (600000629, 'COORDENADORIA DE PROCESSAMENTO INICIAL', 'CPIN');
insert into corporativo.setor (cod_setor, nom_setor, sig_setor) values (600000680, 'SEÇÃO DE RECEBIMENTO E DISTRIBUIÇÃO DE RECURSOS', 'SRDR');

update plataforma.usuario set cod_setor_lotacao = 600000627 where seq_usuario in (4, 5, 6, 7, 8, 9);
update plataforma.usuario set cod_setor_lotacao = 600000629 where seq_usuario in (10, 12);
update plataforma.usuario set cod_setor_lotacao = 600000680 where seq_usuario = 2;