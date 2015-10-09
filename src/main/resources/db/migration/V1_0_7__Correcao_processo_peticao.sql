ALTER TABLE autuacao.peticao ADD num_sedex VARCHAR2(70);
ALTER TABLE autuacao.processo ALTER COLUMN seq_ministro_relator RENAME TO cod_ministro_relator;