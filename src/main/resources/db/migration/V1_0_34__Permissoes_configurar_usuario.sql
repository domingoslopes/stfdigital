-- CRIA TIPO DE INFORMAÇÃO PERMISSÃO, 
-- CRIA A PERMISSÃO PARA CONFIGURAR PERMISSÃO
-- CRIA O RECURSO CONFIGURAR PERMISSÃO
--
-- Permite que o papel GESTOR-AUTUACAO (id 8) execute a ação definida  

INSERT INTO plataforma.tipo_informacao (seq_tipo_informacao, nom_tipo_informacao) VALUES (8, 'PERMISSAO');
INSERT INTO plataforma.permissao (seq_permissao, seq_tipo_informacao, seq_segmento, tip_permissao) VALUES (24, 8, NULL, 'EXECUTAR');
INSERT INTO plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (17, 'configurar-permissao', 'ACAO');

-- PAPEL -> PERMISSAO
INSERT INTO plataforma.permissao_papel (seq_papel, seq_permissao) VALUES (8, 24);
-- PERMISSAO -> RECURSO
INSERT INTO plataforma.permissao_recurso (seq_recurso, seq_permissao) VALUES (17, 24);