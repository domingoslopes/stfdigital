alter table autuacao.processo alter column seq_peticao set null;
alter table autuacao.distribuicao alter column seq_peticao set null;

CREATE TABLE autuacao.processo_processo_workflow (seq_processo bigint not null, num_process_instance bigint not null, constraint pk_processo_processo_workflow primary key (seq_processo, num_process_instance));
alter table autuacao.processo_processo_workflow add constraint FK_PROCESSO_PRPW foreign key (seq_processo) references AUTUACAO.processo;
alter table autuacao.processo_processo_workflow add constraint FK_PROCESSO_WORKFLOW_PRPW foreign key (num_process_instance) references PLATAFORMA.PROCESSO_WORKFLOW;

alter table autuacao.processo drop column tip_situacao;