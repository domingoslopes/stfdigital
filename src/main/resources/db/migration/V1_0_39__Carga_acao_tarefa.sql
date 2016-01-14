INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (24, 'assumir', 5);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (25, 'delegacao', 5);

INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (33, 5, 24, 'EXECUTAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (34, 5, 25, 'EXECUTAR');

INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (27, 'assumir-tarefa', 'ACAO');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (28, 'delegar-tarefa', 'ACAO');

INSERT INTO plataforma.permissao_recurso (seq_permissao, seq_recurso) VALUES (33, 27);
INSERT INTO plataforma.permissao_recurso (seq_permissao, seq_recurso) VALUES (34, 28);

INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (33, 4);
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (33, 5);
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (33, 6);
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (33, 7);
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (33, 8);
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (33, 11);
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (34, 8);