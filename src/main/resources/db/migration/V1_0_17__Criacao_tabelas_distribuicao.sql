create sequence autuacao.seq_distribuicao increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table autuacao.distribuicao (seq_distribuicao bigint not null, dsc_justificativa varchar2(500), dat_distribuicao date not null, sig_usuario_distribuicao varchar2(30) not null, seq_peticao bigint not null, seq_processo bigint not null, tip_distribuicao varchar2(10), constraint pk_distribuicao primary key (seq_distribuicao));
alter table autuacao.distribuicao add constraint FK_PROCESSO_DIST foreign key (seq_processo) references AUTUACAO.processo;
alter table autuacao.distribuicao add constraint FK_PETICAO_DIST foreign key (seq_peticao) references AUTUACAO.peticao;

create table autuacao.distribuicao_min_candidato (seq_distribuicao bigint not null, cod_ministro bigint not null, constraint pk_distribuicao_min_candidato primary key (seq_distribuicao, cod_ministro));
alter table autuacao.distribuicao_min_candidato add constraint FK_DISTRIBUICAO_DIMC foreign key (seq_distribuicao) references AUTUACAO.distribuicao;
alter table autuacao.distribuicao_min_candidato add constraint FK_MINISTRO_DIMC foreign key (cod_ministro) references AUTUACAO.ministro;

create table autuacao.distribuicao_min_impedido (seq_distribuicao bigint not null, cod_ministro bigint not null, constraint pk_distribuicao_min_impedido primary key (seq_distribuicao, cod_ministro));
alter table autuacao.distribuicao_min_impedido add constraint FK_DISTRIBUICAO_DIMI foreign key (seq_distribuicao) references AUTUACAO.distribuicao;
alter table autuacao.distribuicao_min_impedido add constraint FK_PETICAO_DIMI foreign key (cod_ministro) references AUTUACAO.ministro;

create table autuacao.distribuicao_proc_prevento (seq_distribuicao bigint not null, seq_processo bigint not null, constraint pk_distribuicao_proc_prevento primary key (seq_distribuicao, seq_processo));
alter table autuacao.distribuicao_proc_prevento add constraint FK_DISTRIBUICAO_DIPP foreign key (seq_distribuicao) references AUTUACAO.distribuicao;
alter table autuacao.distribuicao_proc_prevento add constraint FK_PROCESSO_DIPP foreign key (seq_processo) references AUTUACAO.processo;