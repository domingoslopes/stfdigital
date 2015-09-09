DROP TABLE plataforma.process_instance_peticao;

CREATE TABLE plataforma.processo_workflow (num_process_instance bigint not null, dsc_status_process_instance varchar(50) not null, primary key (num_process_instance));

CREATE TABLE autuacao.peticao_processo_workflow (seq_peticao bigint not null, num_process_instance bigint not null, primary key (seq_peticao, num_process_instance));