alter table plataforma.papel add column seq_grupo bigint;

update plataforma.papel set seq_grupo = 2 where seq_papel in (4,5,6,7,8);