ALTER TABLE autuacao.peticao ADD qtd_volume INT;
ALTER TABLE autuacao.peticao ADD qtd_apenso INT;
ALTER TABLE autuacao.peticao ADD tip_forma_recebimento VARCHAR2(10);
ALTER TABLE autuacao.peticao ADD tip_meio_peticao VARCHAR2(10) NOT NULL;