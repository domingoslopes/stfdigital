ALTER TABLE autuacao.processo DROP seq_pprocesso;
ALTER TABLE autuacao.peticao ALTER COLUMN dsc_motivo_recusa RENAME TO dsc_motivo_rejeicao;