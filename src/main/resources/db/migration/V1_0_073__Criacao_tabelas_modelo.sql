create sequence corporativo.seq_tipo_modelo increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create table corporativo.tipo_modelo (seq_tipo_modelo bigint not null, dsc_tipo_modelo varchar2(100) not null, constraint pk_tipo_modelo primary key (seq_tipo_modelo));

alter table corporativo.tipo_modelo add constraint uk_timo_dsc_tipo_modelo unique (dsc_tipo_modelo);

create sequence corporativo.seq_modelo increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create table corporativo.modelo (seq_modelo bigint not null, nom_modelo varchar2(100) not null, seq_tipo_modelo bigint not null, seq_documento_template bigint not null, constraint pk_modelo primary key (seq_modelo));

alter table corporativo.modelo add constraint fk_documento_mode foreign key (seq_documento_template) references corporativo.documento(seq_documento);
alter table corporativo.modelo add constraint fk_tipo_modelo_mode foreign key (seq_tipo_modelo) references corporativo.tipo_modelo(seq_tipo_modelo);

alter table corporativo.modelo add constraint uk_mode_seq_tipo_modelo  unique (seq_tipo_modelo, nom_modelo);