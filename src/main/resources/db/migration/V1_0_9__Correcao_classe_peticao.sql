ALTER TABLE autuacao.peticao ALTER COLUMN sig_classe SET NULL;

UPDATE autuacao.peticao SET sig_classe_sugerida = sig_classe, sig_classe = NULL WHERE sig_classe_sugerida IS NULL;
UPDATE autuacao.peticao SET sig_classe_sugerida = sig_classe, sig_classe = sig_classe_sugerida WHERE sig_classe_sugerida != sig_classe;

ALTER TABLE autuacao.peticao ALTER COLUMN sig_classe_sugerida SET NOT NULL;