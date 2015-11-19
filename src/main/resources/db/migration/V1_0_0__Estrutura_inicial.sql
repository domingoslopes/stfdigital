CREATE SCHEMA plataforma;
CREATE SCHEMA corporativo;
CREATE SCHEMA autuacao;

create table autuacao.classe (sig_classe varchar2(6) not null, nom_classe varchar2(80) not null, constraint pk_classe primary key (sig_classe));
create table autuacao.ministro (seq_ministro bigint not null, nom_ministro varchar2(80) not null, constraint pk_ministro primary key (seq_ministro));
create table autuacao.peticao (seq_peticao bigint not null, num_ano_peticao smallint not null, sig_classe varchar2(6) not null, sig_classe_sugerida varchar2(6), dsc_motivo_recusa varchar2(2000), num_peticao bigint not null, tip_status_peticao varchar(255), constraint pk_peticao primary key (seq_peticao));
create table autuacao.peticao_documento (seq_peticao bigint not null, seq_documento bigint not null, constraint pk_peticao_documento primary key (seq_peticao, seq_documento));
create table autuacao.peticao_parte (seq_peticao_parte bigint not null, seq_pessoa bigint not null, tip_polo varchar2(12), seq_peticao bigint, constraint pk_peticao_parte primary key (seq_peticao_parte));
create table autuacao.processo (seq_processo bigint not null, sig_classe varchar2(6) not null, seq_ministro_relator bigint, num_processo bigint not null, seq_peticao bigint not null, seq_pprocesso bigint, constraint pk_processo primary key (seq_processo));
create table autuacao.processo_documento (seq_processo bigint not null, seq_documento bigint not null, constraint pk_processo_documento primary key (seq_processo, seq_documento));
create table autuacao.processo_parte (seq_processo_parte bigint not null, seq_pessoa bigint not null, tip_polo varchar2(12), seq_processo bigint, constraint pk_processo_parte primary key (seq_processo_parte));
create table corporativo.documento (seq_documento bigint not null, bin_conteudo blob, constraint pk_documento primary key (seq_documento));
create table corporativo.pessoa (seq_pessoa bigint not null, nom_pessoa varchar2(4000) not null, constraint pk_pessoa primary key (seq_pessoa));
create table plataforma.process_instance_peticao (num_process_instance bigint not null, seq_peticao bigint not null, constraint pk_process_instance_peticao primary key (num_process_instance));

alter table autuacao.peticao add constraint UK_PETI_NUM_PETICAO  unique (num_peticao, num_ano_peticao);
alter table autuacao.processo add constraint UK_PROC_SIG_CLASSE  unique (sig_classe, num_processo);
alter table autuacao.peticao_documento add constraint FK_PETICAO_PEDO foreign key (seq_peticao) references AUTUACAO.peticao;
alter table autuacao.peticao_parte add constraint FK_PETICAO_PEPA foreign key (seq_peticao) references AUTUACAO.peticao;
alter table autuacao.processo_documento add constraint FK_PROCESSO_PRDO foreign key (seq_processo) references AUTUACAO.processo;
alter table autuacao.processo_parte add constraint FK_PROCESSO_PRPA foreign key (seq_processo) references AUTUACAO.processo;
alter table plataforma.process_instance_peticao add constraint FK_PETICAO_PRIP foreign key (seq_peticao) references AUTUACAO.peticao;

create sequence autuacao.SEQ_MINISTRO increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence autuacao.SEQ_PETICAO increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence autuacao.SEQ_PETICAO_PARTE increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence autuacao.SEQ_PROCESSO increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence autuacao.SEQ_PROCESSO_PARTE increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence corporativo.SEQ_DOCUMENTO increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence corporativo.SEQ_PESSOA increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;