ALTER TABLE autuacao.peticao ADD COLUMN dat_cadastramento DATETIME;
ALTER TABLE autuacao.peticao ADD COLUMN sig_usuario_cadastramento varchar2(30);

UPDATE autuacao.peticao SET dat_cadastramento = SYSDATE, sig_usuario_cadastramento = 'PETICIONADOR';

ALTER TABLE autuacao.peticao ALTER COLUMN dat_cadastramento SET NOT NULL;
ALTER TABLE autuacao.peticao ALTER COLUMN sig_usuario_cadastramento SET NOT NULL;