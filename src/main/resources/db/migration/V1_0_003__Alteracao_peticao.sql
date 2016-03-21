ALTER TABLE autuacao.peticao ADD qtd_volume INT;
ALTER TABLE autuacao.peticao ADD qtd_apenso INT;
ALTER TABLE autuacao.peticao ADD tip_forma_recebimento VARCHAR2(10);
ALTER TABLE autuacao.peticao ADD tip_meio_tramitacao VARCHAR2(10) NOT NULL;
alter table autuacao.peticao add constraint ck_peti_tip_meio_tramitacao check (tip_meio_tramitacao in ('ELETRONICO', 'FISICO'));