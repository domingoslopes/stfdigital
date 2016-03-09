INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (45, 'gerar-texto', 'ACAO');

INSERT INTO plataforma.papel_recurso (seq_papel, seq_recurso) VALUES (7, 45); /* DASHBORAD   -> GESTOR-MODELO   -> VISUALIZAR */