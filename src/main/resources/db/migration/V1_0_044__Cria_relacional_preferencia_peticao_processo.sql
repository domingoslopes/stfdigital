create table autuacao.peticao_preferencia (seq_peticao bigint not null, cod_preferencia bigint not null, constraint pk_peticao_preferencia primary key (seq_peticao, cod_preferencia));
alter table autuacao.peticao add constraint FK_PETICAO_PEPR foreign key (seq_peticao) references AUTUACAO.peticao;
alter table autuacao.preferencia add constraint FK_PREFERENCIA_PEPR foreign key (cod_preferencia) references AUTUACAO.preferencia;

create table autuacao.processo_preferencia (seq_processo bigint not null, cod_preferencia bigint not null, constraint pk_processo_preferencia primary key (seq_processo, cod_preferencia));
alter table autuacao.processo add constraint FK_PROCESSO_PRPR foreign key (seq_processo) references AUTUACAO.processo;
alter table autuacao.preferencia add constraint FK_PREFERENCIA_PRPR foreign key (cod_preferencia) references AUTUACAO.preferencia;