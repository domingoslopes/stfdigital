INSERT INTO plataforma.tipo_informacao (seq_tipo_informacao, nom_tipo_informacao) VALUES (9, 'DASHBOARD');

INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (16, 'peticionamento', 9);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (17, 'recebimento', 9);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (18, 'gestao-recebimento', 9);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (19, 'distribuicao', 9);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (20, 'autuacao', 9);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (21, 'gestao-autuacao', 9);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (22, 'gestao-orgao', 9);
INSERT INTO plataforma.segmento (seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (23, 'gestao-cadastro', 9);

INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (25, 9, 16, 'VISUALIZAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (26, 9, 17, 'VISUALIZAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (27, 9, 18, 'VISUALIZAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (28, 9, 19, 'VISUALIZAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (29, 9, 20, 'VISUALIZAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (30, 9, 21, 'VISUALIZAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (31, 9, 22, 'VISUALIZAR');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (32, 9, 23, 'VISUALIZAR');

INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (19, '1', 'DASHBOARD');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (20, '2', 'DASHBOARD');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (21, '3', 'DASHBOARD');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (22, '4', 'DASHBOARD');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (23, '5', 'DASHBOARD');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (24, '6', 'DASHBOARD');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (25, '7', 'DASHBOARD');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (26, '8', 'DASHBOARD');

INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (19, 25);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (20, 26);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (21, 27);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (22, 28);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (23, 29);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (24, 30);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (25, 31);
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (26, 32);

INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (1, 25);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (2, 26);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (3, 25);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (4, 29);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (5, 29);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (6, 28);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (7, 29);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (8, 29);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (8, 30);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (9, 25);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (9, 31);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (9, 32);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (10, 32);
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (11, 27);