create sequence plataforma.seq_pesquisa_avancada increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create table plataforma.pesquisa_avancada (seq_pesquisa_avancada bigint not null, nom_pesquisa varchar2(300) not null, txt_consulta clob not null, txt_indices varchar2(4000), seq_usuario bigint not null, constraint pk_pesquisa_avancada primary key (seq_pesquisa_avancada));
alter table plataforma.pesquisa_avancada add constraint fk_usuario_peav foreign key (seq_usuario) references plataforma.usuario(seq_usuario);

create sequence plataforma.seq_criterio_pesquisa increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create table plataforma.criterio_pesquisa (seq_criterio_pesquisa bigint not null, dsc_criterio varchar2(100) not null, dsc_campo varchar2(100) not null, dsc_indice varchar2(300) not null, tip_criterio varchar2(10) not null, dsc_api varchar2(4000), dsc_valor varchar2(30), dsc_descricao varchar2(30), constraint pk_criterio_pesquisa primary key (seq_criterio_pesquisa));
alter table plataforma.criterio_pesquisa add constraint ck_crpe_tip_criterio check (tip_criterio in ('NUMERO', 'DATA', 'SELECAO', 'TEXTO', 'BOLEANO'));

insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio) values (plataforma.seq_criterio_pesquisa.nextval, 'Identificação', 'Processo.identificacao', 'distribuicao', 'TEXTO');
insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio, dsc_api, dsc_valor, dsc_descricao) values (plataforma.seq_criterio_pesquisa.nextval, 'Sigla', 'Processo.classe.sigla', 'distribuicao', 'SELECAO', '/classes','sigla', 'nome');
insert into plataforma.criterio_pesquisa (seq_criterio_pesquisa, dsc_criterio, dsc_campo, dsc_indice, tip_criterio) values (plataforma.seq_criterio_pesquisa.nextval, 'Número', 'Processo.numero', 'distribuicao', 'NUMERO');

insert into plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) values (43, 'salvar-pesquisa-avancada', 'ACAO');
insert into plataforma.grupo_recurso (seq_grupo, seq_recurso) values (1, 43);