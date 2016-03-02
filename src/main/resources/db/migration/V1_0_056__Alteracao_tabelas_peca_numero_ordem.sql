alter table autuacao.peticao_peca add column num_ordem_peca number(10, 0);

-- Numera as peças já existentes.
update autuacao.peticao_peca pp
set num_ordem_peca = (
	select sub.num_ordem_peca
	  from (
			select pp.seq_peticao,
			       count(*) as num_ordem_peca,
			       pp.seq_peticao_peca
			from autuacao.peticao_peca as pp
			     left join autuacao.peticao_peca as ppo
			     on pp.seq_peticao = ppo.seq_peticao
			        and pp.seq_peticao_peca >= ppo.seq_peticao_peca
			group by pp.seq_peticao, pp.seq_peticao_peca
	) sub
	where pp.seq_peticao_peca = sub.seq_peticao_peca
);

alter table autuacao.processo_peca add column num_ordem_peca number(10, 0);

-- Numera as peças já existentes.
update autuacao.processo_peca pp
set num_ordem_peca = (
	select sub.num_ordem_peca
	  from (
			select pp.seq_processo,
			       count(*) as num_ordem_peca,
			       pp.seq_processo_peca
			from autuacao.processo_peca as pp
			     left join autuacao.processo_peca as ppo
			     on pp.seq_processo = ppo.seq_processo
			        and pp.seq_processo_peca >= ppo.seq_processo_peca
			group by pp.seq_processo, pp.seq_processo_peca
	) sub
	where pp.seq_processo_peca = sub.seq_processo_peca
);