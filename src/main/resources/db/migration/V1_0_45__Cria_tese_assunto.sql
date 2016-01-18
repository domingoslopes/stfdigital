CREATE SCHEMA jurisprudencia;

create sequence jurisprudencia.seq_tese increment by 1 start with 1905 nomaxvalue minvalue 1 nocycle nocache;

create table jurisprudencia.assunto (cod_assunto varchar2(9) not null, dsc_assunto varchar2(255) not null, constraint pk_assunto primary key (cod_assunto));
alter table jurisprudencia.assunto add constraint UK_ASSU_DSC_ASSUNTO  unique (dsc_assunto);

create table jurisprudencia.tese (seq_tese bigint not null, dsc_tese varchar2(4000), tip_tese varchar2(17) not null, num_tese bigint not null, constraint pk_tese primary key (seq_tese));
alter table jurisprudencia.tese add constraint UK_TESE_DSC_TESE  unique (tip_tese, dsc_tese);
alter table jurisprudencia.tese add constraint UK_TESE_NUM_TESE  unique (tip_tese, num_tese);
alter table jurisprudencia.tese add constraint ck_tese_tip_tese check (tip_tese in ('CONTROVERSIA', 'PRE_TEMA', 'REPERCUSSAO_GERAL'));

create table jurisprudencia.tese_assunto (seq_tese bigint not null, cod_assunto varchar2(9), constraint pk_tese_assunto primary key (seq_tese, cod_assunto));
alter table jurisprudencia.tese_assunto add constraint FK_TESE_TEAS foreign key (seq_tese) references jurisprudencia.tese;
alter table jurisprudencia.tese_assunto add constraint FK_ASSUNTO_TEAS foreign key (cod_assunto) references jurisprudencia.assunto;

Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('864','DIREITO DO TRABALHO');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('899','DIREITO CIVIL');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('1209','DIREITO PROCESSUAL PENAL');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('287','DIREITO PENAL');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('10432','Coisas');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('10431','Responsabilidade Civil');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3520','Crimes contra a Paz Pública');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3523','Crimes contra a Fé Pública');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('7928','Liberdade Provisória');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('7942','Execução Penal');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('7945','Falsidade');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('7947','Fatos Jurídicos');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3547','Crimes Praticados por Funcionários Públicos Contra a Administração em Geral');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3603','Crimes Previstos na Legislação Extravagante');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('9616','Empresas');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('1695','Acordo e Convenção Coletivos de Trabalho');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('1697','Direito Sindical e Questões Análogas');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('1937','Responsabilidade Solidária / Subsidiária');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('2135','Sentença Normativa');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3692','Contravenções Penais');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('4263','Ação Penal');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('4291','Jurisdição e Competência');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('4305','Recurso');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('4310','Fiança');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('4355','Prisão Preventiva');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('4368','Denúncia/Queixa');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('2567','Responsabilidade Civil do Empregador');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('2581','Remuneração, Verbas Indenizatórias e Benefícios');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('2620','Rescisão do Contrato de Trabalho');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('2622','Aposentadoria e Pensão');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('2662','Férias');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3369','Crimes contra a vida');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3385','Lesão Corporal');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3388','Periclitação da Vida e da Saúde e Rixa');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3394','Crimes contra a Honra');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3400','Crimes contra a liberdade pessoal');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3405','Crimes contra a inviolabilidade de domicílio');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3407','Crimes contra a inviolabilidade de correspondência');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3412','Crimes contra a inviolabilidade de segredo');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3415','Crimes contra o Patrimônio');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('5555','Crime Tentado');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3442','Crimes contra a Propriedade Intelectual');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3445','Crimes contra a Organização do Trabalho');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('5626','Família');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('5754','Pessoas naturais');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3457','Crimes contra o sentimento religioso e contra o respeito aos mortos');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3463','Crimes contra a Dignidade Sexual');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3472','Crimes contra a Família');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('3491','Crimes contra a Incolumidade Pública');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('5865','Crime Culposo');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('5872','Crimes Praticados por Particular Contra a Administração em Geral');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('5873','Crimes Praticados por Particular Contra a Administração Pública Estrangeira');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('5874','Crimes Contra a Administração da Justiça');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('5875','Crimes Contra as Finanças Públicas');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('7628','Outras Relações de Trabalho');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('7639','Direito de Greve / Lockout');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('7644','Categoria Profissional Especial');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('7673','Sucessões');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('7681','Obrigações');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('9981','Pessoas Jurídicas');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('7929','Prisão em flagrante');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('7937','Inclusão/exclusão de Jurado');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('10912','Medidas Assecuratórias');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('11414','Crime/contravenção decorrente de conflito fundiário coletivo');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('10949','Violência Doméstica Contra a Mulher');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('10950','Crime / Contravenção contra Criança / Adolescente');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('10951','Crime / Contravenção contra Idoso');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('10952','Fato Atípico');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('10568','Prescrição');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('10604','Investigação Penal');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('10620','Parte Geral');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('10632','Prisão Temporária');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('10904','Prisão Domiciliar / Especial');
Insert into JURISPRUDENCIA.ASSUNTO (COD_ASSUNTO,DSC_ASSUNTO) values ('10891','Habeas Corpus - Cabimento');

Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1492,'Agravo de Instrumento contra decisão que inadmitiu Recurso extraordinário em que se discute, à luz dos arts. 5º, XXXV, e 114, § 2º, da Constituição Federal, a necessidade, ou não, de "comum acordo" como pressuposto processual para o ajuizamento de dissídio coletivo de natureza econômica na Justiça do Trabalho.','PRE_TEMA','921');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1340,'Agravo contra decisão que inadmitiu recurso extraordinário em que se discute, à luz do art. 7, XXVI e 114, I da CF/88 a competência da Justiça do Trabalho para julgar causas envolvendo discussão sobre cláusulas de acordos coletivos de trabalho.','PRE_TEMA','769');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1148,'Recurso Extraordinário com Agravo em que se discute, à luz do artigo 7º, XXIX da Constituição Federal a aplicabilidade da prescrição às ações que tenham por objeto anotação da carteira de trabalho para fins de prova junto à Previdência Social, a teor do artigo 11, § 1º da CLT. ','PRE_TEMA','577');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1036,'Recurso extraordinário com agravo em que se discute, à luz do art. 37, § 6º da Constituição Federal, a responsabilidade objetiva do Estado, em razão de morte de detento.','PRE_TEMA','465');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (173,'Recurso extraordinário em que se discute, à luz dos artigos 5º, LIV e LV; 129, III e VIII; e 144, IV, § 4°, da Constituição Federal, a constitucionalidade, ou não, da realização de procedimento investigatório de natureza penal pelo Ministério Público.','REPERCUSSAO_GERAL','184');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (880,'Recurso extraordinário com agravo em que se discute, à luz dos artigos 5º, caput, incisos II, XXXVI, LIV, LV e 7º, VI, XXX, da Constituição Federal, a possibilidade, ou não, de se reajustar aposentadoria de empregado do antigo Banespa pelo índice do IGP-DI e mais 12% de juros ao ano, ou sucessivamente àqueles conferidos pelas convenções ou acordos coletivos de trabalho, em face da não opção do plano de complementação de aposentadoria. ','PRE_TEMA','309');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1158,'Recurso extraordinário com agravo em que se discute, à luz do artigo 7º, XXIX da Constituição Federal, a aplicabilidade, ou não, da prescrição quinquenal intercorrente no curso do processo de execução trabalhista. ','PRE_TEMA','587');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1160,'Recurso extraordinário com agravo em que se discute, à luz do art. 7º, XXIX, da Constituição Federal, a incidência da decretação da prescrição sobre parcelas trabalhistas não impugnadas na contestação.','PRE_TEMA','589');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1882,'Recurso extraordinário com agravo em que se discute à luz do artigo 7º, XXIX, da Constituição Federal, qual o marco inicial da contagem do prazo prescricional estabelecido no referido dispositivo constitucional, em face da decisão do STF que entendeu que a aposentadoria espontânea do trabalhador não extingue o contrato de trabalho.','CONTROVERSIA','52');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1637,'Agravo de instrumento interposto contra decisão que inadmitiu recurso extraordinário, em que se discute à luz dos arts. 5º, II e XXXV; 37, caput; e 202, da Constituição Federal, a possibilidade, ou não, do pagamento de indenização em dobro, com base na estabilidade decenal, a ex-servidor da Empresa Brasileira de Correios e Telégrafos, referente ao período do regime estatutário regido pela Lei Federal nº 1.711/1.952, anterior à opção pela CLT e FGTS.','PRE_TEMA','1066');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1845,'Recurso Extraordinário com agravo em que se discute, à luz do art. 37, § 6º, da Constituição Federal, a caracterização, ou não, de responsabilidade civil do Estado por danos morais decorrentes de prisão indevida.','CONTROVERSIA','33');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1095,'Recurso extraordinário com agravo em que se discute, à luz dos arts. 5º, II e XXXVI, da Constituição Federal, a possibilidade, ou não, de empresa sucessora ser responsabilizada por danos materiais e morais causados à consumidor da empresa sucedida.','PRE_TEMA','524');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1446,'Agravo em Recurso Extraordinário em que se discute com fundamento no art. 114 da CF/88, sobre a competência para julgamento de prestação de contas de ex-empregado, gestor de negócios, por vendas realizadas em nome do ex-empregador.','PRE_TEMA','875');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (441,'Recurso extraordinário em que se discute, à luz dos artigos 1º, III, e 5º, II, XLVI, LXV, da Constituição Federal, a possibilidade, ou não, de se determinar o cumprimento de pena privativa de liberdade em prisão domiciliar, ante a inexistência de vagas em estabelecimento penitenciário adequado à execução no regime semi-aberto. ','REPERCUSSAO_GERAL','423');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1155,'Agravo em recurso extraordinário em que se discute, à luz do art. 40, § 4º, da Constituição Federal, a possibilidade, ou não, de decisão judicial desconsiderar o quanto foi decidido em acordo coletivo.','PRE_TEMA','584');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (847,'Recurso Extraordinário com Agravo em que se discute, à luz dos artigos 7º, XXIX da Constituição Federal a prescrição incidente sobre pedido de complementação de aposentadoria relativa ao auxílio-alimentação suprimido antes da aposentadoria do empregado e jamais recebido após o jubilamento.','PRE_TEMA','276');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (452,'Agravo de instrumento interposto contra decisão que inadmitiu recurso extraordinário em que se discute, à luz do art. 100 da Constituição Federal, se a PARANAPREVIDÊNCIA faz jus, ou não, ao rito do artigo 730 do CPC, nas hipóteses de execução de quantia em dinheiro.','REPERCUSSAO_GERAL','411');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (224,'Recurso extraordinário em que se discute, à luz dos artigos 5º, II; 37; 59; 84, IV; 146, III, a; 150, I e IV; e 153, III, da Constituição Federal, a natureza jurídica dos juros, a fim de se decidir se verbas recebidas a esse título, em reclamatória trabalhista, se sujeitam, ou não, ao Imposto de Renda.','REPERCUSSAO_GERAL','306');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (814,'Recurso Extraordinário com agravo em que se discute, à luz dos arts. 113 e 114 da CF/88, a incompetência da justiça do trabalho nas ações que envolvam a execução de crédito trabalhista incluído na recuperação judicial de empresas, conforme previsto na Lei Federal nº 11.101/2005.','PRE_TEMA','243');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1781,'Agravo de instrumento contra decisão que não admitiu recurso extraordinário em que se discute, à luz dos arts. 5º, II e XXXVI; e 7º, I, da Constituição Federal, se a aposentadoria espontânea extingue, ou não, o contrato de trabalho.    ','CONTROVERSIA','1');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (817,'Recurso Extraordinário que, à luz do art. 29, inciso VIII, da CF/88, pugna contra acórdão que condenou vereador a pagamento de indenização a título de danos morais por ofensa em pronunciamento feito no exercício de suas funções.','PRE_TEMA','246');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (432,'Recurso extraordinário em que se discute, à luz dos artigos 227 e 229 da Constituição Federal, a constitucionalidade, ou não, de decisão que, com fundamento em interpretação sistemática do art. 75, §1º, da Lei nº 6.815/80, concede ordem de habeas corpus para manter, no território brasileiro, estrangeiro expulso cuja prole brasileira foi concebida posteriormente ao fato motivador do ato expulsório, considerando-se, de um lado, o princípio da soberania nacional e, de outro lado, o princípio da proteção da família.','REPERCUSSAO_GERAL','373');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1145,'Trata-se de recurso extraordinário com agravo em que se discute, à luz dos arts. 5º, II, 7º, XVI, 8º, III e VI, 37 e 173, § 1º, da Constituição Federal, possibilidade, ou não, de incorporação do pagamento do adicional de 15%, pago em razão de trabalho nos fins de semana, previsto em instrumento coletivo, após a supressão do labor aos sábados. ','PRE_TEMA','574');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1672,'Recurso extraordinário com agravo em que se discute a luz do art. 5º, caput e II, da Constituição Federal, a aplicabilidade, ou não, da forma de incidência dos juros de mora, disposto no artigo 1º-F, da lei nº 9.494/1997, com redação da MP nº 2180-35/2001 e da Lei nº 11.960/2009, nos casos de condenação subsidiária da Fazenda Pública.','PRE_TEMA','1101');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (741,'Recurso extraordinário com agravo em que se discute, à luz dos arts. 5º, II, 7º, XXIX, da Constituição Federal, se está prescrito, ou não, com base em acordo coletivo, o direito dos empregados perceberem o pagamento das diferenças de PLR dos exercícios financeiros de 1997, 1998 e 1999, que supostamente foram distribuídos em 2001 apenas aos acionistas como dividendo e juros sobre capital próprio.','PRE_TEMA','170');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1037,'Recurso extraodinário com agravo em que se discute, à luz do art. 7º, XXIX, da Constituição Federal, o prazo da prescrição da ação proposta em razão de acidente de trabalho em face da  emenda Constitucional 45, de 2004.','PRE_TEMA','466');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1267,'.Recurso extraordinário em que se discute, à luz dos artigos 5º, II; 37; 59; 84, IV; 146, III, a; 150, I e IV; e 153, III, da Constituição Federal, a natureza jurídica dos juros, a fim de se decidir se verbas recebidas a esse título, em reclamatória trabalhista, se sujeitam, ou não, ao Imposto de Renda.','PRE_TEMA','696');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1886,'Recurso extraordinário com agravo em que se discute, à luz dos arts. 114, I; e 198, §5º, da Constituição Federal, a competência, ou não, da Justiça do Trabalho para processar e julgar ação que discute verbas trabalhistas, referentes a período regido pela CLT, supostamente devidas a empregados públicos ¿ com fundamento na Emenda Constitucional n.º 51/2006 e na Lei Federal n.º 11.350/2006 ¿ que migraram, posteriormente, para o regime estatutário.','CONTROVERSIA','59');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1082,'Agravo interposto contra decisão que inadmitiu recurso extraordinário em que se discute, à luz do art. 40, § 7º, da Constituição Federal, o direito  de pensionistas de ex-empregados da extinta Ferrovia Paulista S/A ¿ FEPASA, à integralidade no valor da pensão recebida, com base no art. 40, §5º, da Carta Magna, com redação anterior à EC nº 20/1998, e nos artigos 192 e 193 do Decreto Estadual nº 35.530/1959.','PRE_TEMA','511');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1001,'Recurso Extraordinário com agravo em que se discute, à luz do art. 37, § 6º, da Constituição Federal, a responsabilidade civil estatal por danos morais decorrentes de prisão indevida.','PRE_TEMA','430');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (748,'Recurso Extraordinário em que se discute, à luz dos artigos 5º, II, LV e 173, da Constituição Federal, a possibilidade, ou não, de inclusão do Estado no pólo passivo de execução trabalhista movida contra sociedade de economia mista em condição de insolvência.','PRE_TEMA','177');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (749,'Recurso extraordinário com agravo em que se discute, à luz dos arrtigos 5º, XXXV, LIV, LV e 7º, XXIX da Constituição Federal, a extensão de assistência médica supletiva a empregado aposentado por invalidez. Alegação de inexistência de lei ou norma interna concessiva do benefício.','PRE_TEMA','178');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1315,'Recurso extraordinário com agravo em que se discute, à luz dos arts. 37, II, 114, I, da Constituição Federal e do art. 19, §1º, do ADCT, a competência da Justiça do Trabalho para processar e julgar ação que discute a validade, ou não, da mudança do regime jurídico de celetista para estatutário, por meio de lei municipal, de empregado público cujo vínculo com a Administração Pública tenha ocorrido em período anterior à Constituição Federal, sem prévia aprovação em concurso público, e a prescrição em relação aos créditos trabalhistas referentes a período regido pela CLT.','PRE_TEMA','744');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (771,'Recurso extraordinário em que se discute, à luz do art. XXX, da Constituição Federal, se pessoa jurídica de direito privado sem fins lucrativos tem direito, ou não, ao benefício da gratuidade de justiça.','PRE_TEMA','200');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (350,'Recurso extraordinário em que se discute, à luz do art. 5º, XLIII, da Constituição Federal, a possibilidade, ou não, de concessão da liberdade provisória a preso em flagrante pela prática de tráfico ilícito de entorpecentes.','REPERCUSSAO_GERAL','192');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1516,'Recurso extraordinário em que se discute, à luz artigo 5º, inciso LV da CF/88, a possibilidade ou não da cobrança de complementação de seguro facultativo pelos lucros cessantes, do responsável por acidente de veículo causado àquele que utiliza o bem como ferramenta de trabalho e que permaneceu paralisado para conserto das avarias sofridas, o que impossibilitou o exercício das atividades profissionais.','PRE_TEMA','945');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1691,'Agravo instrumento interposto contra decisão que inadmitiu em recurso extraordinário em que se discute, à luz do art. 53, da Constituição Federal, se a imunidade por opiniões, palavras e votos de Deputados e Senadores, alcança, ou não,  a obrigação por responsabilidade civil.','PRE_TEMA','1120');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (516,'Recurso extraordinário com agravo em que se discute, à luz dos incisos I e II do art. 114 da Constituição Federal, a competência para processar e julgar processo que tem por objeto a abusividade de greve de servidores públicos regidos pela Consolidação das Leis do Trabalho – CLT.','REPERCUSSAO_GERAL','544');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (747,'Recurso Extraordinário com Agravo em que se discute, à luz dos art. 7º, XI e XXVI; e 8º, III e VI, da Constituição federal, a possibilidade, ou não, de estabelecer participação nos lucros e resultados da empresa, através de norma coletiva, em desacordo com o estabelecido na Lei nº 10.101/00, bem como, constitucionalidade, ou não, de redução do intervalo intrajornada via cláusula coletiva.','PRE_TEMA','176');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (56,'Recurso extraordinário em que se discute, à luz dos artigos 5º, LV e LX; e art. 93, IX, da Constituição Federal, a constitucionalidade, ou não, do art. 118, § 3º, do Regimento Interno do Superior Tribunal Militar – STM, o qual prevê que o resultado do julgamento de agravo interposto perante aquela Corte será certificado nos autos pela Secretaria do Tribunal Pleno, prescindindo-se da lavratura de acórdão fundamentado.','REPERCUSSAO_GERAL','50');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1354,'Agravo interposto contra decisão que inadmitiu recurso extraordinário em que se discute, à luz do art. 37, II, e §2º, da Constituição Federal, efeitos trabalhistas decorrentes da contratação de empregado por pessoa jurídica de direito privado prestadora de serviço social autônomo, sem submissão à regra da prévia aprovação em concurso público.','PRE_TEMA','783');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (850,'Agravo em Recurso Extraordinário em que se discute, à luz do artigo 5º, II, XXXV, LIV, LV, da Constituição Federal, a existência, ou não, de direito adquirido a empregado do SERPRO com relação ao recebimento do Prêmio Produtividade em virtude do artigo 12 da Lei 5.615/70. ','PRE_TEMA','279');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1334,'Recurso Extraordinário em que se discute à luz do artigo 153, III da Constituição Federal a incidência de imposto de renda sobre abono pecuniário recebido em decorrência de acordo coletivo de trabalho. ','PRE_TEMA','763');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (819,'Recurso extraordinário com agravo em que se discute, à luz do art. 7º, XXIX da Constituição Federal, qual o prazo prescricional, se parcial ou total, para se pleitear complementação de proventos interrompida após aposentadoria por invalidez.','PRE_TEMA','248');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1067,'Prescrição da pretensão de reajuste de complementação de aposentadoria relativos ao abono-complementação instituído pela Fundação Vale do Rio Doce de Seguridade Social - VALIA.
Recurso extraordinário com agravo em que se discute, à luz do art. 7º, inciso XXIV, da Constituição Federal, a prescrição da pretensão de reajuste da complementação de aposentadoria relativos ao abono-complementação instituído pela Fundação Vale do Rio Doce de Seguridade Social - VALIA.','PRE_TEMA','496');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1903,'Recurso extraordinário no qual se discute, à luz do inciso V do art. 170, do caput do art. 207 e do art. 209 da Constituição Federal, se fere a autonomia universitária a decisão que, lastreada no princípio da defesa do consumidor, determina que o pagamento das mensalidades das instituições privadas de ensino superior seja proporcional à quantidade de disciplinas cursadas.','REPERCUSSAO_GERAL','547');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (195,'Recurso extraordinário em que se discute, à luz do art. 114, VI, da Constituição Federal, qual a Justiça competente, se a especializada ou a comum, para processar e julgar as ações indenizatórias decorrentes de acidente do trabalho propostas pelos sucessores do trabalhador falecido.','REPERCUSSAO_GERAL','242');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (839,'Agravo em Recurso Extraordinário em que se discute, à luz dos arts 5º, inciso XXXV, LIV e LV; 93, IX; bem como do art. 10 do ADCT da Constituição Federal, a negativa de prestação jurisdicional em sede de Reclamação Trabalhista que versa sobre dispensa sem justa causa de empregada doméstica.','PRE_TEMA','268');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1460,'Recurso extraordinário com agravo em que se discute, à luz do art. 37, IX, e 39, § 3º, da Constituição Federal, o direito, ou não, ao pagamento de parcelas salariais a servidor público temporário contratado irregularmente.','PRE_TEMA','889');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1663,'Recurso extraordinário com agravo em que se discute, à luz do art. 37, "caput" da CF/88, se servidores temporários tem direito ao recebimento de verbas restritas ao contrato de emprego regido pela CLT. ','PRE_TEMA','1092');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1887,'Recurso extraordinário em que se discute, à luz do art. 37, XVI e XVII, da Constituição Federal, a possibilidade, ou não, da acumulação de proventos com vencimentos de empregados públicos, em face dos efeitos da concessão da aposentadoria espontânea.','CONTROVERSIA','61');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1147,'Agravo em Recurso Extraordinário em que se discute, à luz do art. 114, inciso I da CF/88, a definição da justiça competente para julgar a causa em relação à data da publicação da Lei instituidora do Regime jurídico Único dos servidores, a qual efetivou a transposição do regime celetista para o estatutário.','PRE_TEMA','576');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1526,'Recurso Extraordinário com Agravo em que se discute, à luz do art. 114, I e II da CF/88, a competência, ou não, da Justiça do Trabalho para julgar a abusividade de greve de servidores públicos celetistas.','PRE_TEMA','955');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (170,'Recurso extraordinário em que se discute, à luz dos artigos 5º, XXXVII e LIII; 93, III; 94 e 98, I, da Constituição Federal, a nulidade, ou não, de julgamento realizado por órgão fracionário de tribunal, composto majoritariamente por juízes convocados, tendo em conta os princípios do juiz natural e do duplo grau de jurisdição.','REPERCUSSAO_GERAL','170');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1852,'Recurso extraordinário em que se discute, à luz dos arts. 7º, XXVI, 8º, III e VI, e 202, caput e §2º, da Constituição Federal, a possibilidade, ou não, de o acordo coletivo de trabalho limitar a concessão de uma vantagem - alteração de nível - somente aos empregados em atividade.','CONTROVERSIA','41');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (346,'Recurso extraordinário em que se discute, à luz dos artigos 5º, II, XXII, XXXVI, LIV e LV; e 170, II, da Constituição Federal, a responsabilização, ou não, do empregador por obrigações trabalhistas, no caso de sucessão de empresa.','REPERCUSSAO_GERAL','333');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1617,'Agravo de instrumento interposto contra decisão que inadmitiu recurso extraordinário, em que se discute à luz dos arts. 5°, II, XIII e XXXVI; 100; 173; 175; e 195, §7°, da Constituição Federal, a natureza jurídica de Fundação criada por lei municipal, antes do Novo Código Civil, para fins de execução por ofício precatório. ','PRE_TEMA','1046');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (313,'Recurso extraordinário em que se discute, à luz do art. 125, § 4º, da Constituição Federal, a competência, ou não, de Tribunal de Justiça estadual determinar, no bojo de processo autônomo de perda de posto e patente de militar, a reforma de policial militar, julgado inapto a permanecer nas fileiras da corporação.','REPERCUSSAO_GERAL','358');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (531,'Recurso extraordinário em que se discute, à luz do art. 29, VIII, da Constituição Federal, se a imunidade material de vereador por suas opiniões, palavras e votos alcança, ou não, obrigação de indenizar decorrente de responsabilidade civil.','REPERCUSSAO_GERAL','469');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1121,'Recurso extraordinário em que se discute, à luz do art. 5º, XXXV e XXXVI da CF/1988, a possibilidade, ou não, de redução do valor de multa cominatória, em ação de indenizatória por danos morais ou materiais.','PRE_TEMA','550');
Insert into JURISPRUDENCIA.TESE (SEQ_TESE,DSC_TESE,TIP_TESE,NUM_TESE) values (1322,'Recurso Extraordinário em que se discute, à luz dos arts. 37, § 6º e 5º, LXXVIII da CF/88, a responsabilidade civil objetiva do Estado e a responsabilidade civil pessoal do Magistrado por danos decorrentes da demora na prestação jurisdicional.','PRE_TEMA','751');

Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (432,'10891');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (441,'10904');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (170,'10912');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (747,'1695');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (880,'1695');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1145,'1695');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1155,'1695');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1334,'1695');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1492,'1695');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1852,'1695');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1340,'1697');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (346,'1937');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (748,'1937');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1672,'1937');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (741,'10568');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (749,'10568');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (819,'10568');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (847,'10568');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1037,'10568');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1067,'10568');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1148,'10568');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1158,'10568');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1160,'10568');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1315,'10568');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1882,'10568');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (56,'10604');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (452,'9981');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (771,'9981');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1354,'9981');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1617,'9981');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (350,'7928');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1036,'7928');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (350,'7929');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (441,'7942');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1903,'899');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (195,'2567');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (224,'2581');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (741,'2581');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (747,'2581');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (814,'2581');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (839,'2581');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (850,'2581');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1267,'2581');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1315,'2581');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1460,'2581');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1637,'2581');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1663,'2581');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1886,'2581');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1781,'2620');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1082,'2622');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1882,'2622');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1887,'2622');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (170,'4291');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (313,'4291');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1147,'4291');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1001,'4355');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1845,'4355');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (173,'4368');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (531,'10431');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (817,'10431');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1095,'10431');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1121,'10431');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1322,'10431');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1516,'10431');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1691,'10431');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1446,'7628');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (516,'7639');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1526,'7639');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1516,'7681');
Insert into JURISPRUDENCIA.TESE_ASSUNTO (SEQ_TESE,COD_ASSUNTO) values (1903,'7681');
