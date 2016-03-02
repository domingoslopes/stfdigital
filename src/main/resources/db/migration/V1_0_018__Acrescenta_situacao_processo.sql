alter table autuacao.processo add column tip_situacao varchar2(20);

update autuacao.processo set tip_situacao = 'DISTRIBUIDO';

alter table autuacao.processo alter column tip_situacao set not null;