create sequence autuacao.seq_processo_peca_original increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table autuacao.processo_peca_original (seq_processo_peca_original bigint not null, seq_processo bigint not null, seq_processo_peca bigint not null, seq_documento bigint not null, seq_tipo_peca bigint not null, dsc_peca varchar2(300) not null, num_ordem_peca number(10,0) not null, tip_visibilidade varchar2(25) not null, tip_situacao varchar2(20) not null, tip_vinculo varchar2(10) not null, constraint pk_processo_peca_original primary key (seq_processo_peca_original));
alter table autuacao.processo_peca_original add constraint fk_documento_prpo foreign key (seq_documento) references corporativo.documento(seq_documento);
alter table autuacao.processo_peca_original add constraint fk_processo_prpo foreign key (seq_processo) references autuacao.processo(seq_processo);
alter table autuacao.processo_peca_original add constraint fk_tipo_peca_prpo foreign key (seq_tipo_peca) references autuacao.tipo_peca(seq_tipo_peca);
alter table autuacao.processo_peca_original add constraint ck_prpo_tip_visibilidade check (tip_visibilidade in ('PUBLICO', 'PENDENTE_VISUALIZACAO'));
alter table autuacao.processo_peca_original add constraint ck_prpo_tip_situacao check (tip_situacao in ('EXCLUIDA', 'JUNTADA', 'PENDENTE_JUNTADA'));
alter table autuacao.processo_peca_original add constraint ck_prpo_tip_vinculo check (tip_vinculo in ('DIVISAO', 'UNIAO'));
