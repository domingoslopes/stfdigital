create schema autuacao;

create sequence autuacao.seq_processo increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table autuacao.processo (seq_processo number not null, seq_protocolo number not null, sig_classe varchar2(6), tip_status varchar2(15) not null, constraint pk_processo primary key (seq_processo));
alter table autuacao.processo add constraint ck_proc_tip_status check (tip_status in ('AUTUACAO', 'AUTUADO', 'REJEITADO'));