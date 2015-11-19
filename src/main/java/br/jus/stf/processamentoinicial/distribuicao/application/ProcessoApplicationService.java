package br.jus.stf.processamentoinicial.distribuicao.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.distribuicao.domain.TarefaAdapter;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Distribuicao;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.DistribuicaoComum;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.DistribuicaoPrevencao;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ParametroDistribuicao;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoRepository;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.TipoDistribuicao;

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
	@Qualifier("processoTarefaRestAdapter")
	private TarefaAdapter tarefaAdapter;
	
	@Autowired
	private ProcessoRepository processoRepository;

	@Autowired
	private ProcessoApplicationEvent processoApplicationEvent;
	
	/**
	 * Distribui um processo para um Ministro Relator.
	 * 
	 * @param peticao Dados da petição.
	 * @param ministroRelator Dados do Ministro Relator do processo.
	 * @return processo
	 */
	public Processo distribuir(TipoDistribuicao tipoDistribuicao, ParametroDistribuicao parametroDistribuicao) {
		tarefaAdapter.completarDistribuicao(parametroDistribuicao.peticao().processoWorkflowId());
		Distribuicao distribuicao;
		
		switch(tipoDistribuicao) {
			case COMUM:
				distribuicao = new DistribuicaoComum(parametroDistribuicao);
				break;
			case PREVENCAO:
				distribuicao = new DistribuicaoPrevencao(parametroDistribuicao);
				break;
			default:
				throw new IllegalArgumentException("Tipo de distribuição inexistente: " + tipoDistribuicao);
		}
		
		Processo processo = distribuicao.executar();
		processo.associarDistribuicao(distribuicao);
		processoRepository.save(processo);
		processoApplicationEvent.processoDistribuido(processo);
		return processo;
	}

}
