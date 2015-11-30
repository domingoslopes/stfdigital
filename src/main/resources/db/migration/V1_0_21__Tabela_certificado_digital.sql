create sequence plataforma.seq_certificado_digital increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table plataforma.certificado_digital (SEQ_CERTIFICADO_DIGITAL bigint not null, SEQ_USUARIO bigint, DSC_CERTIFICADO_DIGITAL varchar2(768) not null, COD_SERIAL varchar2(60) not null, TXT_CERTIFICADO_DIGITAL clob not null, DAT_VALIDADE_INICIAL date not null, DAT_VALIDADE_FINAL date not null, SEQ_CERTIFICADO_EMISSOR bigint not null, TIP_CERTIFICADO_DIGITAL varchar2(1) not null, TIP_PKI varchar2(15) not null, constraint pk_certificado_digital primary key (seq_certificado_digital));
alter table plataforma.certificado_digital add constraint uk_cod_serial_cedi unique(cod_serial, seq_certificado_emissor);
alter table plataforma.certificado_digital add constraint FK_usuario_cedi foreign key (seq_usuario) references plataforma.usuario;
alter table plataforma.certificado_digital add constraint ck_tip_certificado_digital_cedi check (tip_certificado_digital in ('A', 'F', 'R'));
alter table plataforma.certificado_digital add constraint ck_tip_pki_cedi check (tip_pki in ('ICP_BRASIL'));