create sequence plataforma.seq_informacao increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table plataforma.informacao (seq_informacao bigint not null, seq_tipo_informacao bigint not null, seq_segmento bigint, cod_identidade varchar2(30) not null, constraint pk_informacao primary key (seq_informacao));
alter table plataforma.informacao add constraint uk_info_seq_tipo_informacao unique (seq_tipo_informacao, seq_segmento, cod_identidade);
alter table plataforma.informacao add constraint FK_TIPO_INFORMACAO_INFO foreign key (seq_tipo_informacao) references plataforma.tipo_informacao;
alter table plataforma.informacao add constraint FK_SEGMENTO_INFO foreign key (seq_segmento) references plataforma.segmento;