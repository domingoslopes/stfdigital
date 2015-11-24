create sequence plataforma.seq_usuario increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence plataforma.seq_tipo_segmento increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence plataforma.seq_tipo_informacao increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence plataforma.seq_segmento increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence plataforma.seq_permissao increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence plataforma.seq_recurso increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence plataforma.seq_grupo increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence plataforma.seq_papel increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table plataforma.tipo_segmento (seq_tipo_segmento bigint not null, nom_tipo_segmento varchar2(100) not null, constraint pk_tipo_segmento primary key (seq_tipo_segmento));
alter table plataforma.tipo_segmento add constraint uk_tise_nom_tipo_segmento unique(nom_tipo_segmento);

create table plataforma.tipo_informacao (seq_tipo_informacao bigint not null, nom_tipo_informacao varchar2(100) not null, constraint pk_tipo_informacao primary key (seq_tipo_informacao));
alter table plataforma.tipo_informacao add constraint uk_tiin_nom_tipo_informacao unique(nom_tipo_informacao);

create table plataforma.segmento (seq_segmento bigint not null, nom_segmento varchar2(100) not null, seq_tipo_informacao bigint not null, seq_tipo_segmento bigint, constraint pk_segmento primary key (seq_segmento));
alter table plataforma.segmento add constraint uk_segm_nom_segmento unique(nom_segmento, seq_tipo_informacao, seq_tipo_segmento);
alter table plataforma.tipo_informacao add constraint FK_TIPO_INFORMACAO_SEGM foreign key (seq_tipo_informacao) references plataforma.tipo_informacao;
alter table plataforma.tipo_segmento add constraint FK_TIPO_SEGMENTO_SEGM foreign key (seq_tipo_segmento) references plataforma.tipo_segmento;

create table plataforma.permissao (seq_permissao bigint not null, seq_segmento bigint not null, tip_permissao varchar2(20), constraint pk_permissao primary key (seq_permissao));
alter table plataforma.permissao add constraint uk_perm_seq_segmento unique(seq_segmento, tip_permissao);
alter table plataforma.segmento add constraint FK_SEGMENTO_PERM foreign key (seq_segmento) references plataforma.segmento;
alter table plataforma.permissao add constraint ck_perm_tip_permissao check (tip_permissao in ('ALTERAR', 'CRIAR', 'EXCLUIR', 'EXECUTAR', 'IMPRIMIR', 'PESQUISAR', 'VISUALIZAR'));

create table plataforma.usuario (seq_usuario bigint not null, nom_usuario varchar2(100) not null, sig_usuario varchar2(30) not null, cod_cpf varchar2(11) not null, cod_oab varchar2(20), dsc_email varchar2(50) not null, dsc_telefone varchar2(14), constraint pk_usuario primary key (seq_usuario));
alter table plataforma.usuario add constraint uk_usua_sig_usuario unique(sig_usuario);

create table plataforma.permissao_usuario (seq_permissao bigint not null, seq_usuario bigint not null, constraint pk_permissao_usuario primary key (seq_permissao, seq_usuario));
alter table plataforma.permissao_usuario add constraint FK_USUARIO_PEUS foreign key (seq_usuario) references plataforma.usuario;
alter table plataforma.permissao_usuario add constraint FK_PERMISSAO_PEUS foreign key (seq_permissao) references plataforma.permissao;

create table plataforma.recurso (seq_recurso bigint not null, nom_recurso varchar2(50) not null, tip_recurso varchar2(20) not null, constraint pk_recurso primary key (seq_recurso));
alter table plataforma.recurso add constraint uk_recu_nom_recurso unique(nom_recurso, tip_recurso);
alter table plataforma.recurso add constraint ck_recu_tip_recurso check (tip_recurso in ('ACAO', 'DASHBOARD', 'NOTIFICACAO', 'PESQUISA', 'TAREFA'));

create table plataforma.permissao_recurso (seq_permissao bigint not null, seq_recurso bigint not null, constraint pk_permissao_recurso primary key (seq_permissao, seq_recurso));
alter table plataforma.permissao_recurso add constraint FK_RECURSO_PERE foreign key (seq_recurso) references plataforma.recurso;
alter table plataforma.permissao_recurso add constraint FK_PERMISSAO_PERE foreign key (seq_permissao) references plataforma.permissao;

create table plataforma.grupo (seq_grupo bigint not null, nom_grupo varchar2(100) not null, tip_grupo varchar2(20) not null, constraint pk_grupo primary key (seq_grupo));
alter table plataforma.grupo add constraint uk_grup_nom_grupo unique(nom_grupo, tip_grupo);
alter table plataforma.grupo add constraint ck_grup_tip_grupo check (tip_grupo in ('ADVOGADO', 'CIDADAO', 'ORGAO', 'SETOR'));

create table plataforma.permissao_grupo (seq_permissao bigint not null, seq_grupo bigint not null, constraint pk_permissao_grupo primary key (seq_permissao, seq_grupo));
alter table plataforma.permissao_grupo add constraint FK_GRUPO_PEGR foreign key (seq_grupo) references plataforma.grupo;
alter table plataforma.permissao_grupo add constraint FK_PERMISSAO_PEGR foreign key (seq_permissao) references plataforma.permissao;

create table plataforma.grupo_usuario (seq_grupo bigint not null, seq_usuario bigint not null, constraint pk_grupo_usuario primary key (seq_grupo, seq_usuario));
alter table plataforma.grupo_usuario add constraint FK_GRUPO_GRUS foreign key (seq_grupo) references plataforma.grupo;
alter table plataforma.grupo_usuario add constraint FK_USUARIO_GRUS foreign key (seq_usuario) references plataforma.usuario;

create table plataforma.papel (seq_papel bigint not null, nom_papel varchar2(100) not null, constraint pk_papel primary key (seq_papel));
alter table plataforma.papel add constraint uk_pape_nom_papel unique(nom_papel);

create table plataforma.papel_usuario (seq_papel bigint not null, seq_usuario bigint not null, constraint pk_papel_usuario primary key (seq_papel, seq_usuario));
alter table plataforma.papel_usuario add constraint FK_papel_PAUS foreign key (seq_papel) references plataforma.papel;
alter table plataforma.papel_usuario add constraint FK_USUARIO_PAUS foreign key (seq_usuario) references plataforma.usuario;

create table plataforma.permissao_papel (seq_permissao bigint not null, seq_papel bigint not null, constraint pk_permissao_papel primary key (seq_permissao, seq_papel));
alter table plataforma.permissao_papel add constraint FK_PAPEL_PEPA foreign key (seq_papel) references plataforma.papel;
alter table plataforma.permissao_papel add constraint FK_PERMISSAO_PEPA foreign key (seq_permissao) references plataforma.permissao;
