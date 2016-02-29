create table plataforma.usuario_recurso (seq_usuario bigint not null, seq_recurso bigint not null, constraint pk_usuario_recurso primary key (seq_usuario, seq_recurso));
alter table plataforma.usuario_recurso add constraint FK_USUARIO_USRE foreign key (seq_usuario) references plataforma.usuario;
alter table plataforma.usuario_recurso add constraint FK_RECURSO_USRE foreign key (seq_recurso) references plataforma.recurso;

create table plataforma.grupo_recurso (seq_grupo bigint not null, seq_recurso bigint not null, constraint pk_grupo_recurso primary key (seq_grupo, seq_recurso));
alter table plataforma.grupo_recurso add constraint FK_GRUPO_GRRE foreign key (seq_grupo) references plataforma.grupo;
alter table plataforma.grupo_recurso add constraint FK_RECURSO_GRRE foreign key (seq_recurso) references plataforma.recurso;

create table plataforma.papel_recurso (seq_papel bigint not null, seq_recurso bigint not null, constraint pk_papel_recurso primary key (seq_papel, seq_recurso));
alter table plataforma.papel_recurso add constraint FK_PAPEL_PARE foreign key (seq_papel) references plataforma.papel;
alter table plataforma.papel_recurso add constraint FK_RECURSO_PARE foreign key (seq_recurso) references plataforma.recurso;