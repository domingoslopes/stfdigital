ALTER TABLE corporativo.documento ALTER COLUMN bin_conteudo SET NULL;

UPDATE corporativo.documento SET bin_conteudo = NULL;

ALTER TABLE corporativo.documento ALTER COLUMN bin_conteudo RENAME TO num_conteudo;
ALTER TABLE corporativo.documento ALTER COLUMN num_conteudo VARCHAR(24);

UPDATE corporativo.documento SET num_conteudo = '55fc6afe0b3c7c7f0625ed48';

ALTER TABLE corporativo.documento ALTER COLUMN num_conteudo SET NOT NULL;