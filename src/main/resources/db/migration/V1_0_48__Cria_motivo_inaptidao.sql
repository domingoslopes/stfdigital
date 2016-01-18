create table autuacao.motivo_inaptidao (cod_motivo_inaptidao bigint not null, dsc_motivo_inaptidao varchar2(500) not null, constraint pk_motivo_inaptidao primary key (cod_motivo_inaptidao));
alter table autuacao.motivo_inaptidao add constraint UK_MOIN_DSC_MOTIVO_INAPTIDAO  unique (dsc_motivo_inaptidao);

Insert into AUTUACAO.MOTIVO_INAPTIDAO (COD_MOTIVO_INAPTIDAO,DSC_MOTIVO_INAPTIDAO) values ('2','Ausência de preliminar formal de repercussão geral');
Insert into AUTUACAO.MOTIVO_INAPTIDAO (COD_MOTIVO_INAPTIDAO,DSC_MOTIVO_INAPTIDAO) values ('3','Decisão agravada com base na sistemática da repercussão geral (art. 543-B, CPC)');
Insert into AUTUACAO.MOTIVO_INAPTIDAO (COD_MOTIVO_INAPTIDAO,DSC_MOTIVO_INAPTIDAO) values ('4','Intempestividade do agravo');
Insert into AUTUACAO.MOTIVO_INAPTIDAO (COD_MOTIVO_INAPTIDAO,DSC_MOTIVO_INAPTIDAO) values ('5','Intempestividade do recurso extraordinário');
Insert into AUTUACAO.MOTIVO_INAPTIDAO (COD_MOTIVO_INAPTIDAO,DSC_MOTIVO_INAPTIDAO) values ('9','Outro');
Insert into AUTUACAO.MOTIVO_INAPTIDAO (COD_MOTIVO_INAPTIDAO,DSC_MOTIVO_INAPTIDAO) values ('6','Recurso extraordinário contra decisão monocrática');
Insert into AUTUACAO.MOTIVO_INAPTIDAO (COD_MOTIVO_INAPTIDAO,DSC_MOTIVO_INAPTIDAO) values ('7','Recurso extraordinário julgado deserto na origem');
Insert into AUTUACAO.MOTIVO_INAPTIDAO (COD_MOTIVO_INAPTIDAO,DSC_MOTIVO_INAPTIDAO) values ('8','Supressão de instância');