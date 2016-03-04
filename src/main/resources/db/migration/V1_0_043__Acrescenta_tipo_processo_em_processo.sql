alter table autuacao.processo add column tip_processo varchar2(10);

update autuacao.processo set tip_processo = 'ORIGINARIO';

alter table autuacao.processo alter column tip_processo set not null;

alter table autuacao.processo add constraint ck_proc_tip_processo check (tip_processo in ('ORIGINARIO', 'RECURSAL'));