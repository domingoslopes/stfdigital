alter table corporativo.texto add column seq_documento_final bigint;

alter table corporativo.texto add constraint fk_documento_final_text foreign key (seq_documento_final) references corporativo.documento(seq_documento);