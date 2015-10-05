create sequence autuacao.seq_orgao;

create table autuacao.orgao (seq_orgao bigint not null, nom_orgao varchar2(200) not null, primary key (seq_orgao));

INSERT INTO autuacao.orgao (seq_orgao, nom_orgao) VALUES (autuacao.seq_orgao.nextval, 'ADVOCACIA-GERAL DA UNIÃO');
INSERT INTO autuacao.orgao (seq_orgao, nom_orgao) VALUES (autuacao.seq_orgao.nextval, 'DEFENSORIA PÚBLICA DA UNIÃO');
INSERT INTO autuacao.orgao (seq_orgao, nom_orgao) VALUES (autuacao.seq_orgao.nextval, 'MINISTÉRIO DA JUSTIÇA');
INSERT INTO autuacao.orgao (seq_orgao, nom_orgao) VALUES (autuacao.seq_orgao.nextval, 'MINISTÉRIO PÚBLICO DO DISTRITO FEDERAL E TERRITÓRIOS');
INSERT INTO autuacao.orgao (seq_orgao, nom_orgao) VALUES (autuacao.seq_orgao.nextval, 'PROCURADORIA DA FAZENDA NACIONAL');
INSERT INTO autuacao.orgao (seq_orgao, nom_orgao) VALUES (autuacao.seq_orgao.nextval, 'PROCURADORIA-GERAL DA REPÚBLICA');
INSERT INTO autuacao.orgao (seq_orgao, nom_orgao) VALUES (autuacao.seq_orgao.nextval, 'SUPERIOR TRIBUNAL DE JUSTIÇA');
INSERT INTO autuacao.orgao (seq_orgao, nom_orgao) VALUES (autuacao.seq_orgao.nextval, 'SUPERIOR TRIBUNAL MILITAR');

alter table autuacao.peticao add column seq_orgao_representado bigint;
alter table autuacao.peticao add constraint fk_orgao_peti foreign key (seq_orgao_representado) references autuacao.orgao(seq_orgao);