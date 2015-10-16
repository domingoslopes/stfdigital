package br.jus.stf.processamentoinicial.distribuicao.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.TarefaAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoRepository;
import br.jus.stf.shared.MinistroId;

/**
 * @author Lucas Rodrigues
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.09.2015
 */
@Component
public class ProcessoApplicationService {

	@Autowired
	private TarefaAdapter tarefaAdapter;
	
	@Autowired
	private ProcessoRepository processoRepository;

	// TODO: Remover essa dependência com o módulo de petições
	@Autowired
	private ProcessoApplicationEvent processoApplicationEvent;
	
	/**
	 * Distribui um processo para um Ministro Relator.
	 * 
	 * @param peticao Dados da petição.
	 * @param ministroRelator Dados do Ministro Relator do processo.
	 * @return processo
	 */
	public Processo distribuir(Peticao peticao, MinistroId ministroRelator) {
		tarefaAdapter.completarDistribuicao(peticao);
		Processo processo = peticao.distribuir(ministroRelator);
		processoRepository.save(processo);
		processoApplicationEvent.processoDistribuido(processo);
		return processo;
	}

}
