INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (15, 'UI', 4);

UPDATE plataforma.permissao SET seq_segmento = 15 WHERE seq_permissao = 11;

UPDATE plataforma.recurso SET nom_recurso = 'notificar-ui' WHERE seq_recurso = 11;