alter table autuacao.peticao_peca add column tip_visibilidade varchar2(25);
alter table autuacao.peticao_peca add constraint ck_pepe_tip_visibilidade check (tip_visibilidade in ('PUBLICO', 'PENDENTE_VISUALIZACAO'));
alter table autuacao.peticao_peca add column tip_situacao varchar2(20);
alter table autuacao.peticao_peca add constraint ck_pepe_tip_situacao check (tip_situacao in ('EXCLUIDA', 'JUNTADA', 'PENDENTE_JUNTADA'));

alter table autuacao.processo_peca add column tip_visibilidade varchar2(25);
alter table autuacao.processo_peca add constraint ck_prpe_tip_visibilidade check (tip_visibilidade in ('PUBLICO', 'PENDENTE_VISUALIZACAO'));
alter table autuacao.processo_peca add column tip_situacao varchar2(20);
alter table autuacao.processo_peca add constraint ck_prpe_tip_situacao check (tip_situacao in ('EXCLUIDA', 'JUNTADA', 'PENDENTE_JUNTADA'));

update autuacao.peticao_peca set tip_visibilidade = 'PUBLICO', tip_situacao = 'JUNTADA';

update autuacao.processo_peca set tip_visibilidade = 'PUBLICO', tip_situacao = 'JUNTADA';

ALTER TABLE autuacao.peticao_peca ALTER COLUMN tip_visibilidade SET NOT NULL;
ALTER TABLE autuacao.peticao_peca ALTER COLUMN tip_situacao SET NOT NULL;

ALTER TABLE autuacao.processo_peca ALTER COLUMN tip_visibilidade SET NOT NULL;
ALTER TABLE autuacao.processo_peca ALTER COLUMN tip_situacao SET NOT NULL;