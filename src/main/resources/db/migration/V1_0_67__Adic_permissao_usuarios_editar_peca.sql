/* Ação EDITAR-PECA */
INSERT INTO plataforma.segmento(seq_segmento, nom_segmento, seq_tipo_informacao) VALUES (38, 'EDITAR-PECA', 5);

/* Permissão de EXECUTAR para essa ação */
INSERT INTO plataforma.permissao(seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (47, 5, 38, 'EXECUTAR');

/* Adição da permissão aos papéis*/
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 21); /* TAREFA -> EXCLUIR PEÇA -> EXECUTAR -> PAPEL ORGANIZADOR-PECAS */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 2);  /* TAREFA -> INSERIR PEÇA -> EXECUTAR -> PAPEL RECEBEDOR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 15); /* TAREFA -> INSERIR PEÇA -> EXECUTAR -> PAPEL AUTUADOR RECURSAL CRIMINAL/ELEITORAL */ 
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 5);  /* TAREFA -> INSERIR PEÇA -> EXECUTAR -> PAPEL AUTUADOR-ORIGINÁRIO */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 20); /* TAREFA -> INSERIR PEÇA -> EXECUTAR -> PAPEL AUTUADOR-RECURSAL */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 6);  /* TAREFA -> INSERIR PEÇA -> EXECUTAR -> PAPEL DISTRIBUIDOR */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 7);  /* TAREFA -> INSERIR PEÇA -> EXECUTAR -> PAPEL CARTORÁRIA */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 8);  /* TAREFA -> INSERIR PEÇA -> EXECUTAR -> PAPEL GESTOR-AUTUAÇÃO */ 
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 11); /* TAREFA -> INSERIR PEÇA -> EXECUTAR -> PAPEL GESTOR-RECEBIMENTO */ 
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 14); /* TAREFA -> INSERIR PEÇA -> EXECUTAR -> PAPEL PREAUTUADOR-RECURSAL */ 
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 4);  /* TAREFA -> INSERIR PEÇA -> EXECUTAR -> PAPEL PREAUTUADOR-ORIGINÁRIO */ 
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 16); /* TAREFA -> INSERIR PEÇA -> EXECUTAR -> PAPEL ANALISTA DE PRESSUPOSTOS */ 
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 18); /* TAREFA -> INSERIR PEÇA -> EXECUTAR -> PAPEL ANALISTA DE REPERCUSSÃO GERAL */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 17); /* TAREFA -> INSERIR PEÇA -> EXECUTAR -> PAPEL REVISOR DE PROCESSOS INAPTOS */
INSERT INTO plataforma.permissao_papel (seq_permissao, seq_papel) VALUES (47, 19); /* TAREFA -> INSERIR PEÇA -> EXECUTAR -> PAPEL REVISOR DE REPERCUSSÃO GERAL */