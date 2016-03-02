/* Ação EXCLUIR-PECA */
INSERT INTO plataforma.segmento(seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (35, 'EXCLUIR-PECAS', 5);

/* Permissão de EXECUTAR para essa ação */
INSERT INTO plataforma.permissao(seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (44, 5, 35, 'EXECUTAR');

/* Adição da permissão aos papéis*/
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (44, 21);  /* TAREFA -> EXCLUIRR PEÇA -> EXECUTAR -> PAPEL ORGANIZADOR-PECAS */