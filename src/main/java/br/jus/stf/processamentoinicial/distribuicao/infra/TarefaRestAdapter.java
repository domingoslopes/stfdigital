package br.jus.stf.processamentoinicial.distribuicao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.workflow.interfaces.TarefaRestResource;
import br.jus.stf.plataforma.workflow.interfaces.commands.CompletarTarefaCommand;
import br.jus.stf.plataforma.workflow.interfaces.dto.TarefaDto;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoStatus;
import br.jus.stf.processamentoinicial.autuacao.infra.eventbus.PeticaoStatusModificado;
import br.jus.stf.processamentoinicial.distribuicao.domain.TarefaAdapter;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Peticao;
import reactor.bus.Event;
import reactor.bus.EventBus;

/**
 * @author Rafael Alencar
 * 
 * @since 09.11.2015
 */
@Component("processoTarefaRestAdapter")
public class TarefaRestAdapter implements TarefaAdapter {

	@Autowired
	private TarefaRestResource tarefaRestResource;
	
	@Autowired
	private EventBus eventBus;

	@Override
	public void completarDistribuicao(Peticao peticao) {
		completarTarefaPorProcesso(peticao, PeticaoStatus.DISTRIBUIDA);	
	}
	
	/**
	 * Completa uma tarefa do processo com um status
	 * 
	 * @param peticao
	 */
	private void completarTarefaPorProcesso(Peticao peticao, PeticaoStatus status) {
		CompletarTarefaCommand command = new CompletarTarefaCommand();
		command.setStatus(status.toString());
		TarefaDto dto = tarefaRestResource.consultarPorProcesso(peticao.processoWorkflowId().toLong());
		tarefaRestResource.completar(dto.getId(), command);
		eventBus.notify("indexadorEventBus", Event.wrap(new PeticaoStatusModificado(peticao.id(), peticao.tipo(), peticao.processoWorkflowId(), status)));
	}

}
