create sequence autuacao.seq_motivo_devolucao increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create table autuacao.motivo_devolucao (seq_motivo_devolucao bigint not null, dsc_motivo_devolucao varchar2(500) not null, constraint pk_motivo_devolucao primary key (seq_motivo_devolucao));
alter table autuacao.motivo_devolucao add constraint uk_mode_dsc_motivo_devolucao  unique (dsc_motivo_devolucao);

create table autuacao.motivo_devolucao_tipo_modelo (seq_motivo_devolucao bigint not null, seq_tipo_modelo bigint not null, constraint pk_motivo_devolucao_tipo_model primary key (seq_motivo_devolucao, seq_tipo_modelo));
alter table autuacao.motivo_devolucao_tipo_modelo add constraint fk_tipo_modelo_mdtm foreign key (seq_tipo_modelo) references corporativo.tipo_modelo(seq_tipo_modelo);
alter table autuacao.motivo_devolucao_tipo_modelo add constraint fk_motivo_devolucao_mdtm foreign key (seq_motivo_devolucao) references autuacao.motivo_devolucao(seq_motivo_devolucao);

Insert into autuacao.motivo_devolucao (seq_motivo_devolucao, dsc_motivo_devolucao) values (autuacao.seq_motivo_devolucao.nextval, 'Remessa indevida');
Insert into autuacao.motivo_devolucao_tipo_modelo (seq_motivo_devolucao, seq_tipo_modelo) values (autuacao.seq_motivo_devolucao.currval, 8);

Insert into autuacao.motivo_devolucao (seq_motivo_devolucao, dsc_motivo_devolucao) values (autuacao.seq_motivo_devolucao.nextval, 'Transitado');
Insert into autuacao.motivo_devolucao_tipo_modelo (seq_motivo_devolucao, seq_tipo_modelo) values (autuacao.seq_motivo_devolucao.currval, 8);

Insert into autuacao.motivo_devolucao (seq_motivo_devolucao, dsc_motivo_devolucao) values (autuacao.seq_motivo_devolucao.nextval, 'Baixado');
Insert into autuacao.motivo_devolucao_tipo_modelo (seq_motivo_devolucao, seq_tipo_modelo) values (autuacao.seq_motivo_devolucao.currval, 8);