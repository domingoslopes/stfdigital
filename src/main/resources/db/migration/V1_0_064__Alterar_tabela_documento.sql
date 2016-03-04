ALTER TABLE corporativo.documento ADD tamanho number(10,0);

UPDATE corporativo.documento set tamanho = 1024;

ALTER TABLE corporativo.documento ALTER COLUMN tamanho SET not null;