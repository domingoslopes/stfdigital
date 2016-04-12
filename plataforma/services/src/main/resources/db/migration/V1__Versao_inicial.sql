create schema identificador;

create sequence identificador.seq_identificador increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table identificador.identificador (dsc_categoria varchar2(50) not null, num_identificador number not null, constraint pk_identificador primary key (dsc_categoria));