DROP TABLE plataforma.process_instance_peticao;

CREATE TABLE plataforma.processo_workflow (num_process_instance bigint not null, dsc_status_process_instance varchar2(50) not null, constraint pk_processo_workflow primary key (num_process_instance));

CREATE TABLE autuacao.peticao_processo_workflow (seq_peticao bigint not null, num_process_instance bigint not null, constraint pk_peticao_processo_workflow primary key (seq_peticao, num_process_instance));

alter table autuacao.peticao_processo_workflow add constraint FK_PETICAO_PEPW foreign key (seq_peticao) references AUTUACAO.peticao;
alter table autuacao.peticao_processo_workflow add constraint FK_PROCESSO_WORKFLOW_PEPW foreign key (num_process_instance) references PLATAFORMA.PROCESSO_WORKFLOW;