alter table autuacao.peticao add column tip_processo varchar2(10);

update autuacao.peticao set tip_processo = 'ORIGINARIO';

alter table autuacao.peticao alter column tip_processo set not null;

alter table autuacao.peticao add constraint ck_peti_tip_processo check (tip_processo in ('ORIGINARIO', 'RECURSAL'));