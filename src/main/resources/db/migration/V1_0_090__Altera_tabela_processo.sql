alter table autuacao.processo add column dat_recebimento date;
update autuacao.processo proc set proc.dat_recebimento = (select peti.dat_cadastramento from autuacao.peticao peti where proc.seq_peticao = peti.seq_peticao);
alter table autuacao.processo alter column dat_recebimento set not null;

alter table autuacao.processo add column dat_autuacao date;

alter table autuacao.processo add tip_meio_tramitacao VARCHAR2(10);
update autuacao.processo proc set proc.tip_meio_tramitacao = (select peti.tip_meio_tramitacao from autuacao.peticao peti where proc.seq_peticao = peti.seq_peticao);
alter table autuacao.processo alter column tip_meio_tramitacao set not null;
alter table autuacao.processo add constraint ck_proc_tip_meio_tramitacao check (tip_meio_tramitacao in ('ELETRONICO', 'FISICO'));

alter table autuacao.processo add tip_sigilo VARCHAR2(15) default 'PUBLICO' not null;
alter table autuacao.processo add constraint ck_proc_tip_sigilo check (tip_sigilo in ('PUBLICO', 'SEGREDO_JUSTICA'));