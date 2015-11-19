create sequence plataforma.seq_usuario increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence plataforma.seq_tipo_segmento increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence plataforma.seq_tipo_informacao increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence plataforma.seq_segmento increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence plataforma.seq_permissao increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence plataforma.seq_recurso increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table plataforma.tipo_segmento (seq_tipo_segmento bigint not null, nom_tipo_segmento varchar2(100) not null, constraint pk_tipo_segmento primary key (seq_tipo_segmento));

create table plataforma.tipo_informacao (seq_tipo_informacao bigint not null, nom_tipo_informacao varchar2(100) not null, constraint pk_tipo_informacao primary key (seq_tipo_informacao));

create table plataforma.segmento (seq_segmento bigint not null, nom_segmento varchar2(100) not null, seq_tipo_informacao bigint not null, seq_tipo_segmento bigint, constraint pk_segmento primary key (seq_segmento));
alter table plataforma.tipo_informacao add constraint FK_TIPO_INFORMACAO_SEGM foreign key (seq_tipo_informacao) references plataforma.tipo_informacao;
alter table plataforma.tipo_segmento add constraint FK_TIPO_SEGMENTO_SEGM foreign key (seq_tipo_segmento) references plataforma.tipo_segmento;

create table plataforma.permissao (seq_permissao bigint not null, seq_segmento bigint not null, tip_permissao varchar2(20), constraint pk_permissao primary key (seq_permissao));
alter table plataforma.segmento add constraint FK_SEGMENTO_PERM foreign key (seq_segmento) references plataforma.segmento;

create table plataforma.usuario (seq_usuario bigint not null, nom_usuario varchar2(100) not null, sig_usuario varchar2(30) not null, cod_cpf varchar2(11) not null, cod_oab varchar2(20), dsc_email varchar2(50) not null, dsc_telefone varchar2(14), constraint pk_usuario primary key (seq_usuario));
alter table plataforma.usuario add constraint uk_sig_usuario_usua unique(sig_usuario);

create table plataforma.permissao_usuario (seq_permissao bigint not null, seq_usuario bigint not null, constraint pk_permissao_usuario primary key (seq_permissao, seq_usuario));
alter table plataforma.permissao_usuario add constraint FK_USUARIO_PEUS foreign key (seq_usuario) references plataforma.usuario;
alter table plataforma.permissao_usuario add constraint FK_PERMISSAO_PEUS foreign key (seq_permissao) references plataforma.permissao;

create table plataforma.recurso (seq_recurso bigint not null, nom_recurso varchar2(50) not null, tip_recurso varchar2(20) not null, constraint pk_recurso primary key (seq_recurso));

create table plataforma.permissao_recurso (seq_permissao bigint not null, seq_recurso bigint not null, constraint pk_permissao_recurso primary key (seq_permissao, seq_recurso));
alter table plataforma.permissao_recurso add constraint FK_RECURSO_PERE foreign key (seq_recurso) references plataforma.recurso;
alter table plataforma.permissao_recurso add constraint FK_PERMISSAO_PERE foreign key (seq_permissao) references plataforma.permissao;