--Cria a ação excluir-pesquisa-avancada
insert into plataforma.recurso (seq_recurso, nom_recurso, tip_recurso) VALUES (50, 'excluir-pesquisa-avancada', 'ACAO');
insert into plataforma.grupo_recurso (seq_grupo, seq_recurso) VALUES (1, 50);