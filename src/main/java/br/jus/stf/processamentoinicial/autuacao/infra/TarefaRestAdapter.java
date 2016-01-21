package br.jus.stf.processamentoinicial.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import br.jus.stf.plataforma.workflow.interfaces.TarefaRestResource;
import br.jus.stf.plataforma.workflow.interfaces.commands.CompletarTarefaCommand;
import br.jus.stf.plataforma.workflow.interfaces.dto.TarefaDto;
import br.jus.stf.processamentoinicial.autuacao.domain.TarefaAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoStatus;
import br.jus.stf.shared.PeticaoStatusModificado;
import br.jus.stf.shared.ProcessoWorkflow;
import br.jus.stf.shared.ProcessoWorkflowId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component("peticaoTarefaRestAdapter")
public class TarefaRestAdapter implements TarefaAdapter {

	@Autowired
	private TarefaRestResource tarefaRestResource;

	@Autowired
	private PeticaoRepository peticaoRepository;
	
	@Autowired
	private EventBus eventBus;
	
	@Override
	public void completarDevolucao(Peticao peticao) {
		completarTarefaPorProcesso(peticao, PeticaoStatus.DEVOLVIDA);		
	}

	@Override
	public void completarAutuacao(Peticao peticao) {
		completarTarefaPorProcesso(peticao, PeticaoStatus.ACEITA);		
	}
	
	@Override
	public void completarAutuacaoRejeitada(Peticao peticao) {
		completarTarefaPorProcesso(peticao, PeticaoStatus.REJEITADA);		
	}

	@Override
	public void completarPreautuacao(Peticao peticao) {
		completarTarefaPorProcesso(peticao, PeticaoStatus.A_AUTUAR);
	}
	
	@Override
	public void completarPreautuacaoIndevida(Peticao peticao) {
		completarTarefaPorProcesso(peticao, PeticaoStatus.A_DEVOLVER);
	}

	@Override
	public void completarDistribuicao(Peticao peticao) {
		completarTarefaPorProcesso(peticao, PeticaoStatus.DISTRIBUIDA);	
	}
	
	@Override
	public void completarPreparacaoParaDevolucao(Peticao peticao) {
		completarTarefaPorProcesso(peticao, PeticaoStatus.ASSINAR_DEVOLUCAO);
	}
	
	/**
	 * Completa uma tarefa do processo com um status
	 * 
	 * @param processoWorkflowId
	 */
	private void completarTarefaPorProcesso(Peticao peticao, PeticaoStatus status) {
		ProcessoWorkflow workflow = peticao.processosWorkflow().iterator().next();
		ProcessoWorkflowId id = workflow.id();
		CompletarTarefaCommand command = new CompletarTarefaCommand();
		command.setStatus(status.toString());
		TarefaDto dto = tarefaRestResource.consultarPorProcesso(id.toLong());
		tarefaRestResource.completar(dto.getId(), command);
		peticaoRepository.refresh(peticao); // O comando acima poderá alterar o status da petição, por isso o refresh.
		eventBus.notify("indexadorEventBus", Event.wrap(new PeticaoStatusModificado(peticao.id(), peticao.getClass().getSimpleName(), workflow.id(), status)));
	}

}
