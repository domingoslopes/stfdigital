alter table autuacao.peticao add column seq_texto_devolucao bigint;

alter table autuacao.peticao add constraint fk_texto_peti foreign key (seq_texto_devolucao) references corporativo.texto(seq_texto);