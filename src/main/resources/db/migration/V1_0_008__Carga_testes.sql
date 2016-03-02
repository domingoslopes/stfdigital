INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'LUZIA FERREIRA DA SILVA');
INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'CELSO RUI DOMINGUES');
INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'LUIZ EDUARDO DE CASTILHO GIROTTO');
INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'GLAUCO SCHUMACHER');
INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'ROBERTO DONATO BARBOZA PIRES DOS REIS');
INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'SAMUEL LUIZ XAVIER');
INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'SAULO KRICHANA RODRIGUES');
INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'ANTONIO JOSE SANDOVAL');
INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'VLADIMIR ANTONIO RIOLI');
INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'JULIO SERGIO GOMES DE ALMEIDA');
INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'PAOLA ZANELATO');
INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'RUTH STEFANELLI WAGNER VALLEJO');
INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'NELITO CELSO VILLETTI');
INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'ADRIANE BARROS DE OLIVEIRA NUNES');
INSERT INTO corporativo.pessoa (seq_pessoa, nom_pessoa) VALUES (corporativo.seq_pessoa.nextval, 'ZENIR NEITZKE');

INSERT INTO corporativo.documento VALUES (corporativo.seq_documento.nextval, FILE_READ('classpath:banner.txt'));

INSERT INTO autuacao.peticao (seq_peticao, num_ano_peticao, num_peticao, sig_classe, tip_meio_peticao) VALUES (autuacao.seq_peticao.nextval, 2015, 1, 'ADI', 'ELETRONICO');

INSERT INTO autuacao.peticao_parte (seq_peticao_parte, seq_peticao, seq_pessoa, tip_polo) VALUES (autuacao.seq_peticao_parte.nextval, autuacao.seq_peticao.currval, 1, 'POLO_ATIVO'); 
INSERT INTO autuacao.peticao_parte (seq_peticao_parte, seq_peticao, seq_pessoa, tip_polo) VALUES (autuacao.seq_peticao_parte.nextval, autuacao.seq_peticao.currval, 2, 'POLO_ATIVO');
INSERT INTO autuacao.peticao_parte (seq_peticao_parte, seq_peticao, seq_pessoa, tip_polo) VALUES (autuacao.seq_peticao_parte.nextval, autuacao.seq_peticao.currval, 3, 'POLO_PASSIVO');
INSERT INTO autuacao.peticao_parte (seq_peticao_parte, seq_peticao, seq_pessoa, tip_polo) VALUES (autuacao.seq_peticao_parte.nextval, autuacao.seq_peticao.currval, 4, 'POLO_PASSIVO');

INSERT INTO autuacao.peticao_documento (seq_peticao, seq_documento) VALUES (autuacao.seq_peticao.currval, 1);

INSERT INTO autuacao.peticao (seq_peticao, num_ano_peticao, num_peticao, sig_classe, qtd_volume, qtd_apenso, tip_forma_recebimento, num_sedex, tip_meio_peticao) VALUES (autuacao.seq_peticao.nextval, 2015, 2, 'ADPF', 10, 1, 'SEDEX', 'SS123456789BR', 'FISICO');

INSERT INTO autuacao.peticao_parte (seq_peticao_parte, seq_peticao, seq_pessoa, tip_polo) VALUES (autuacao.seq_peticao_parte.nextval, autuacao.seq_peticao.currval, 5, 'POLO_ATIVO'); 
INSERT INTO autuacao.peticao_parte (seq_peticao_parte, seq_peticao, seq_pessoa, tip_polo) VALUES (autuacao.seq_peticao_parte.nextval, autuacao.seq_peticao.currval, 6, 'POLO_ATIVO');
INSERT INTO autuacao.peticao_parte (seq_peticao_parte, seq_peticao, seq_pessoa, tip_polo) VALUES (autuacao.seq_peticao_parte.nextval, autuacao.seq_peticao.currval, 7, 'POLO_PASSIVO');
INSERT INTO autuacao.peticao_parte (seq_peticao_parte, seq_peticao, seq_pessoa, tip_polo) VALUES (autuacao.seq_peticao_parte.nextval, autuacao.seq_peticao.currval, 9, 'POLO_PASSIVO');

INSERT INTO autuacao.peticao_documento (seq_peticao, seq_documento) VALUES (autuacao.seq_peticao.currval, 1);

INSERT INTO autuacao.peticao (seq_peticao, num_ano_peticao, num_peticao, sig_classe, tip_meio_peticao, dsc_motivo_rejeicao) VALUES (autuacao.seq_peticao.nextval, 2015, 3, 'ADI', 'ELETRONICO', 'Comprovação insuficiente.');

INSERT INTO autuacao.peticao_parte (seq_peticao_parte, seq_peticao, seq_pessoa, tip_polo) VALUES (autuacao.seq_peticao_parte.nextval, autuacao.seq_peticao.currval, 10, 'POLO_ATIVO'); 
INSERT INTO autuacao.peticao_parte (seq_peticao_parte, seq_peticao, seq_pessoa, tip_polo) VALUES (autuacao.seq_peticao_parte.nextval, autuacao.seq_peticao.currval, 11, 'POLO_PASSIVO');

INSERT INTO autuacao.peticao_documento (seq_peticao, seq_documento) VALUES (autuacao.seq_peticao.currval, 1);

INSERT INTO autuacao.peticao (seq_peticao, num_ano_peticao, num_peticao, sig_classe, qtd_volume, qtd_apenso, tip_forma_recebimento, tip_meio_peticao, sig_classe_sugerida) VALUES (autuacao.seq_peticao.nextval, 2015, 4, 'ACO', 5, 2, 'BALCAO', 'FISICO', 'ACO');

INSERT INTO autuacao.peticao_parte (seq_peticao_parte, seq_peticao, seq_pessoa, tip_polo) VALUES (autuacao.seq_peticao_parte.nextval, autuacao.seq_peticao.currval, 12, 'POLO_ATIVO'); 
INSERT INTO autuacao.peticao_parte (seq_peticao_parte, seq_peticao, seq_pessoa, tip_polo) VALUES (autuacao.seq_peticao_parte.nextval, autuacao.seq_peticao.currval, 13, 'POLO_PASSIVO');

INSERT INTO autuacao.peticao_documento (seq_peticao, seq_documento) VALUES (autuacao.seq_peticao.currval, 1);

INSERT INTO autuacao.processo (seq_processo, sig_classe, num_processo, cod_ministro_relator, seq_peticao) VALUES (autuacao.seq_processo.nextval, 'ACO', 200, 28, autuacao.seq_peticao.currval);

INSERT INTO autuacao.processo_parte (seq_processo_parte, seq_processo, seq_pessoa, tip_polo) VALUES (autuacao.seq_processo_parte.nextval, autuacao.seq_processo.currval, 12, 'POLO_ATIVO'); 
INSERT INTO autuacao.processo_parte (seq_processo_parte, seq_processo, seq_pessoa, tip_polo) VALUES (autuacao.seq_processo_parte.nextval, autuacao.seq_processo.currval, 13, 'POLO_PASSIVO');

INSERT INTO autuacao.processo_documento (seq_processo, seq_documento) VALUES (autuacao.seq_processo.currval, 1);

INSERT INTO autuacao.peticao (seq_peticao, num_ano_peticao, num_peticao, sig_classe, tip_meio_peticao, sig_classe_sugerida) VALUES (autuacao.seq_peticao.nextval, 2015, 5, 'ACO', 'ELETRONICO', 'OACO');

INSERT INTO autuacao.peticao_parte (seq_peticao_parte, seq_peticao, seq_pessoa, tip_polo) VALUES (autuacao.seq_peticao_parte.nextval, autuacao.seq_peticao.currval, 14, 'POLO_ATIVO'); 
INSERT INTO autuacao.peticao_parte (seq_peticao_parte, seq_peticao, seq_pessoa, tip_polo) VALUES (autuacao.seq_peticao_parte.nextval, autuacao.seq_peticao.currval, 15, 'POLO_PASSIVO');

INSERT INTO autuacao.peticao_documento (seq_peticao, seq_documento) VALUES (autuacao.seq_peticao.currval, 1);

INSERT INTO autuacao.processo (seq_processo, sig_classe, num_processo, cod_ministro_relator, seq_peticao) VALUES (autuacao.seq_processo.nextval, 'OACO', 201, 46, autuacao.seq_peticao.currval);

INSERT INTO autuacao.processo_parte (seq_processo_parte, seq_processo, seq_pessoa, tip_polo) VALUES (autuacao.seq_processo_parte.nextval, autuacao.seq_processo.currval, 14, 'POLO_ATIVO'); 
INSERT INTO autuacao.processo_parte (seq_processo_parte, seq_processo, seq_pessoa, tip_polo) VALUES (autuacao.seq_processo_parte.nextval, autuacao.seq_processo.currval, 15, 'POLO_PASSIVO');

INSERT INTO autuacao.processo_documento (seq_processo, seq_documento) VALUES (autuacao.seq_processo.currval, 1);

COMMIT;