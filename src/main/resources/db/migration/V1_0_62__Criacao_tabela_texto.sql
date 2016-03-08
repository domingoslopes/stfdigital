create sequence corporativo.seq_texto increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table corporativo.texto (seq_texto bigint not null, seq_documento bigint not null, constraint pk_texto primary key (seq_texto));

alter table corporativo.texto add constraint fk_documento_text foreign key (seq_documento) references corporativo.documento(seq_documento);