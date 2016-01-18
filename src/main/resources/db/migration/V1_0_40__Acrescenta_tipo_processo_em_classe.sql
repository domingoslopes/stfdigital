alter table autuacao.classe add column tip_processo varchar2(10);

update autuacao.classe set tip_processo = case when sig_classe in ('ARE', 'AI', 'RE') then 'RECURSAL' else 'ORIGINARIO' end;

alter table autuacao.classe alter column tip_processo set not null;

alter table autuacao.classe add constraint ck_clas_tip_processo check (tip_processo in ('ORIGINARIO', 'RECURSAL'));