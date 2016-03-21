alter table autuacao.peticao add column dat_autuacao date;
alter table autuacao.peticao add tip_sigilo VARCHAR2(15) default 'PUBLICO' not null;
alter table autuacao.peticao add constraint ck_peti_tip_sigilo check (tip_sigilo in ('PUBLICO', 'SEGREDO_JUSTICA'));