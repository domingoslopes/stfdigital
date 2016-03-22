create sequence autuacao.seq_processo_origem increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create table autuacao.processo_origem (seq_processo_origem bigint not null, seq_processo bigint not null, cod_tribunal_juizo bigint not null, seq_unidade_federacao bigint not null, num_processo bigint not null, flg_principal varchar2(1) default 'N' not null, constraint pk_processo_origem primary key (seq_processo_origem));
alter table autuacao.processo_origem add constraint uk_pror_seq_processo unique (seq_processo, cod_tribunal_juizo, seq_unidade_federacao, num_processo);
alter table autuacao.processo_origem add constraint fk_processo_pror foreign key (seq_processo) references autuacao.processo(seq_processo);
alter table autuacao.processo_origem add constraint fk_tribunal_juizo_pror foreign key (cod_tribunal_juizo) references autuacao.tribunal_juizo(cod_tribunal_juizo);
alter table autuacao.processo_origem add constraint fk_unidade_federacao_pror foreign key (seq_unidade_federacao) references corporativo.unidade_federacao(seq_unidade_federacao);
alter table autuacao.processo_origem add constraint ck_pror_flg_principal check (flg_principal in ('Y', 'N'));