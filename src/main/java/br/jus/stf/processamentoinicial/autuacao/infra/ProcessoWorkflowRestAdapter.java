package br.jus.stf.processamentoinicial.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.EventBus;
import br.jus.stf.plataforma.workflow.interfaces.WorkflowRestResource;
import br.jus.stf.plataforma.workflow.interfaces.commands.IniciarProcessoCommand;
import br.jus.stf.processamentoinicial.autuacao.domain.WorkflowAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoStatus;
import br.jus.stf.shared.ProcessoWorkflow;
import br.jus.stf.shared.ProcessoWorkflowId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class ProcessoWorkflowRestAdapter implements WorkflowAdapter {
	
	@Autowired
	private WorkflowRestResource processoRestService;

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private PeticaoRepository peticaoRepository;
	
	@Override
	public void iniciarWorkflow(PeticaoEletronica peticaoEletronica) {
		IniciarProcessoCommand command = new IniciarProcessoCommand();
		command.setMensagem("autuacao");
		command.setStatus(PeticaoStatus.A_AUTUAR.toString());
		command.setInformacao(peticaoEletronica.id().toString());
		command.setTipoInformacao(peticaoEletronica.getClass().getSimpleName());
		command.setDescricao(peticaoEletronica.identificacao());
		
		Long id = processoRestService.iniciar(command);
		peticaoEletronica.associarProcessoWorkflow(new ProcessoWorkflow(new ProcessoWorkflowId(id), PeticaoStatus.A_AUTUAR.name()));
		peticaoRepository.save(peticaoEletronica);
	}

	@Override
	public void iniciarWorkflow(PeticaoFisica peticaoFisica) {
		IniciarProcessoCommand command = new IniciarProcessoCommand();
		command.setMensagem("remessaFisica");
		command.setStatus(PeticaoStatus.A_PREAUTUAR.toString());
		command.setInformacao(peticaoFisica.id().toString());
		command.setTipoInformacao(peticaoFisica.getClass().getSimpleName());
		command.setDescricao(peticaoFisica.identificacao());
		
		Long id = processoRestService.iniciarPorMensagem(command);
		peticaoFisica.associarProcessoWorkflow(new ProcessoWorkflow(new ProcessoWorkflowId(id), PeticaoStatus.A_PREAUTUAR.name()));
		peticaoRepository.save(peticaoFisica);
	}
	
}
