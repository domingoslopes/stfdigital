--Altera o nome da ação, de devolver-peticao, para gerar-texto-devolucao.
update plataforma.recurso set nom_recurso = 'gerar-texto-devolucao' where seq_recurso = 7;

--Altera o nome da ação, de assinar-devolucao-peticao, para devolver-peticao.
update plataforma.recurso set nom_recurso = 'devolver-peticao' where seq_recurso = 15;

--Cria a ação finalizar-texto-devolucao
insert into plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (47, 'finalizar-texto-devolucao', 'ACAO');
insert into plataforma.papel_recurso (seq_papel, seq_recurso) VALUES (7, 47); /* TAREFA FINALIZAR-TEXTO-DEVOLUCAO   -> PAPEL CARTORARIA  ->  EXECUTAR   */