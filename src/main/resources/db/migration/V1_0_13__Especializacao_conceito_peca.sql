create sequence autuacao.seq_tipo_peca increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table autuacao.tipo_peca (seq_tipo_peca bigint not null, nom_tipo_peca varchar2(100) not null, constraint pk_tipo_peca primary key (seq_tipo_peca));

insert into autuacao.tipo_peca (seq_tipo_peca, nom_tipo_peca) values (autuacao.seq_tipo_peca.nextval, 'Petição inicial');
INSERT INTO autuacao.tipo_peca (seq_tipo_peca, nom_tipo_peca) VALUES (autuacao.seq_tipo_peca.nextval, 'Custas');
insert into autuacao.tipo_peca (seq_tipo_peca, nom_tipo_peca) values (autuacao.seq_tipo_peca.nextval, 'Cópia do ato normativo ou lei impugnada');
INSERT INTO autuacao.tipo_peca (seq_tipo_peca, nom_tipo_peca) VALUES (autuacao.seq_tipo_peca.nextval, 'Decisão rescindenda');
insert into autuacao.tipo_peca (seq_tipo_peca, nom_tipo_peca) values (autuacao.seq_tipo_peca.nextval, 'Ato coator');
INSERT INTO autuacao.tipo_peca (seq_tipo_peca, nom_tipo_peca) VALUES (autuacao.seq_tipo_peca.nextval, 'Decisão ou ato reclamado');
insert into autuacao.tipo_peca (seq_tipo_peca, nom_tipo_peca) values (autuacao.seq_tipo_peca.nextval, 'Decisão impugnada');

create sequence autuacao.seq_peticao_peca increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
alter table autuacao.peticao_documento rename to autuacao.peticao_peca;
alter table autuacao.peticao_peca add column seq_tipo_peca bigint;
alter table autuacao.peticao_peca add column dsc_peca varchar2(300);
alter table autuacao.peticao_peca add column seq_peticao_peca bigint;
alter table autuacao.peticao_peca drop constraint fk_peticao_pedo;
alter table autuacao.peticao_peca drop primary key;
alter table autuacao.peticao_peca add constraint fk_documento_pepe foreign key (seq_documento) references corporativo.documento(seq_documento);
alter table autuacao.peticao_peca add constraint fk_peticao_pepe foreign key (seq_peticao) references autuacao.peticao(seq_peticao);
alter table autuacao.peticao_peca add constraint fk_tipo_peca_pepe foreign key (seq_tipo_peca) references autuacao.tipo_peca(seq_tipo_peca);

update autuacao.peticao_peca set seq_peticao_peca = autuacao.seq_peticao_peca.nextval, seq_tipo_peca = 1, dsc_peca = 'Petição inicial';

alter table autuacao.peticao_peca alter column seq_tipo_peca set not null;
alter table autuacao.peticao_peca alter column dsc_peca set not null;
alter table autuacao.peticao_peca alter column seq_peticao_peca set not null;
alter table autuacao.peticao_peca add constraint pk_peticao_peca primary key (seq_peticao_peca);

create sequence autuacao.seq_processo_peca increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
alter table autuacao.processo_documento rename to autuacao.processo_peca;
alter table autuacao.processo_peca add column seq_tipo_peca bigint;
alter table autuacao.processo_peca add column dsc_peca varchar2(300);
alter table autuacao.processo_peca add column seq_processo_peca bigint;
alter table autuacao.processo_peca drop constraint fk_processo_prdo;
alter table autuacao.processo_peca drop primary key;
alter table autuacao.processo_peca add constraint fk_documento_prpe foreign key (seq_documento) references corporativo.documento(seq_documento);
alter table autuacao.processo_peca add constraint fk_processo_prpe foreign key (seq_processo) references autuacao.processo(seq_processo);
alter table autuacao.processo_peca add constraint fk_tipo_peca_prpe foreign key (seq_tipo_peca) references autuacao.tipo_peca(seq_tipo_peca);

update autuacao.processo_peca set seq_processo_peca = autuacao.seq_processo_peca.nextval, seq_tipo_peca = 1, dsc_peca = 'Petição inicial';

alter table autuacao.processo_peca alter column seq_tipo_peca set not null;
alter table autuacao.processo_peca alter column dsc_peca set not null;
alter table autuacao.processo_peca alter column seq_processo_peca set not null;
alter table autuacao.processo_peca add constraint pk_processo_peca primary key (seq_processo_peca);