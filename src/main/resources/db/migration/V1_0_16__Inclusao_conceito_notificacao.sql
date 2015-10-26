create table plataforma.notificacao (seq_notificacao bigint not null, tip_notificacao varchar2(10) not null, txt_mensagem varchar2(500) not null, usu_notificante varchar2(50) not null, dat_criacao timestamp not null, primary key (seq_notificacao));
create table plataforma.notificado (seq_notificado bigint not null, flg_leu char(1) not null, dat_leitura timestamp, usu_notificado varchar2(50) not null, seq_notificacao bigint not null, primary key (seq_notificado));

alter table plataforma.notificado add constraint fk_notificacao_noti foreign key (seq_notificacao) references plataforma.notificacao;

create sequence plataforma.seq_notificacao;
create sequence plataforma.seq_notificado;