create sequence plataforma.seq_pesquisa_avancada increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create table plataforma.pesquisa_avancada (seq_pesquisa_avancada bigint not null, nom_pesquisa varchar2(300) not null, txt_consulta clob not null, txt_indices varchar2(4000), seq_usuario bigint not null, constraint pk_pesquisa_avancada primary key (seq_pesquisa_avancada));
alter table plataforma.pesquisa_avancada add constraint fk_usuario_peav foreign key (seq_usuario) references plataforma.usuario(seq_usuario);

create sequence plataforma.seq_criterio_pesquisa increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create table plataforma.criterio_pesquisa (seq_criterio_pesquisa bigint not null, dsc_criterio varchar2(100) not null, dsc_campo varchar2(100) not null, tip_criterio varchar2(10) not null, txt_consulta clob not null, dsc_valor varchar2(30) not null, dsc_descricao varchar2(30) not null, constraint pk_criterio_pesquisa primary key (seq_criterio_pesquisa));
alter table plataforma.criterio_pesquisa add constraint ck_crpe_tip_criterio check (tip_criterio in ('NUMERO', 'DATA', 'SELECAO', 'TEXTO', 'BOOLEANO'));