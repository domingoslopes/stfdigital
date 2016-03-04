create table autuacao.processo_assunto (seq_processo bigint not null, cod_assunto varchar2(9) not null, constraint pk_processo_assunto primary key (seq_processo, cod_assunto));
alter table autuacao.processo_assunto add constraint FK_ASSUNTO_PRAS foreign key (cod_assunto) references jurisprudencia.assunto;

create table autuacao.processo_tese (seq_processo bigint not null, seq_tese bigint not null, constraint pk_processo_tese primary key (seq_processo, seq_tese));
alter table autuacao.processo_tese add constraint FK_TESE_PRTE foreign key (seq_tese) references jurisprudencia.tese;

create sequence autuacao.seq_processo_motivo_inaptidao increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table autuacao.processo_motivo_inaptidao (seq_processo_motivo_inaptidao bigint not null, seq_processo bigint not null, cod_motivo_inaptidao bigint not null, dsc_motivo_inaptidao varchar2(500), constraint pk_processo_motivo_inaptidao primary key (seq_processo_motivo_inaptidao));
alter table autuacao.processo_motivo_inaptidao add constraint FK_PROCESSO_PRMI foreign key (seq_processo) references autuacao.processo;
alter table autuacao.processo_motivo_inaptidao add constraint FK_MOTIVO_INAPTIDAO_PRMI foreign key (cod_motivo_inaptidao) references autuacao.motivo_inaptidao;
alter table autuacao.processo_motivo_inaptidao add constraint UK_PRMI_SEQ_PROCESSO unique (seq_processo, cod_motivo_inaptidao);

ALTER TABLE autuacao.processo ADD dsc_analise VARCHAR2(1000);
ALTER TABLE autuacao.processo ADD tip_classificacao VARCHAR2(6);
alter table autuacao.processo add constraint ck_proc_tip_classificacao check (tip_classificacao in ('APTO', 'INAPTO'));