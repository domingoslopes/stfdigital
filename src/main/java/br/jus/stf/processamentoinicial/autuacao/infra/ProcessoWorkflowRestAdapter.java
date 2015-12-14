package br.jus.stf.processamentoinicial.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.workflow.interfaces.WorkflowRestResource;
import br.jus.stf.plataforma.workflow.interfaces.commands.IniciarProcessoCommand;
import br.jus.stf.plataforma.workflow.interfaces.commands.SinalizarCommand;
import br.jus.stf.processamentoinicial.autuacao.domain.WorkflowAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoStatus;
import br.jus.stf.processamentoinicial.autuacao.infra.eventbus.PeticaoStatusModificado;
import br.jus.stf.shared.ProcessoWorkflow;
import br.jus.stf.shared.ProcessoWorkflowId;
import reactor.bus.Event;
import reactor.bus.EventBus;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class ProcessoWorkflowRestAdapter implements WorkflowAdapter {

	private static final String PETICAO_INVALIDA = "Petição Inválida";
	private static final String REMESSA_INDEVIDA = "Remessa Indevida";
	
	@Autowired
	private WorkflowRestResource processoRestService;

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private PeticaoRepository peticaoRepository;
	
	@Override
	public void iniciarWorkflow(PeticaoEletronica peticaoEletronica) {
		IniciarProcessoCommand command = new IniciarProcessoCommand();
		command.setMensagem("autuarOriginarios");
		command.setStatus(PeticaoStatus.A_AUTUAR.toString());
		command.setInformacao(peticaoEletronica.id().toLong());
		command.setTipoInformacao(peticaoEletronica.getClass().getSimpleName());
		command.setDescricao(peticaoEletronica.identificacao());
		
		Long id = processoRestService.iniciar(command);
		peticaoEletronica.associarProcessoWorkflow(new ProcessoWorkflow(new ProcessoWorkflowId(id), PeticaoStatus.A_AUTUAR.name()));
	}

	@Override
	public void iniciarWorkflow(PeticaoFisica peticaoFisica) {
		IniciarProcessoCommand command = new IniciarProcessoCommand();
		command.setMensagem("Remessa de Petições Físicas");
		command.setStatus(PeticaoStatus.A_PREAUTUAR.toString());
		command.setInformacao(peticaoFisica.id().toLong());
		command.setTipoInformacao(peticaoFisica.getClass().getSimpleName());
		command.setDescricao(peticaoFisica.identificacao());
		
		Long id = processoRestService.iniciarPorMensagem(command);
		peticaoFisica.associarProcessoWorkflow(new ProcessoWorkflow(new ProcessoWorkflowId(id), PeticaoStatus.A_PREAUTUAR.name()));
	}
	
	@Override
	public void rejeitarAutuacao(Peticao peticao) {
		ProcessoWorkflow processoWorkflow = peticao.processosWorkflow().iterator().next();
		ProcessoWorkflowId id = processoWorkflow.id();
		SinalizarCommand command = new SinalizarCommand();
		command.setSinal(PETICAO_INVALIDA);
		PeticaoStatus novoStatus = PeticaoStatus.REJEITADA;
		command.setStatus(novoStatus.toString());

		processoRestService.sinalizar(id.toLong(), command);
		peticaoRepository.refresh(peticao); // O comando acima poderá alterar o status da petição, por isso o refresh.
		notifyPeticaoStatusModificado(peticao, processoWorkflow.id(), novoStatus);
	}
	
	@Override
	public void devolver(Peticao peticao) {
		ProcessoWorkflow processoWorkflow = peticao.processosWorkflow().iterator().next();
		ProcessoWorkflowId id = processoWorkflow.id();
		SinalizarCommand command = new SinalizarCommand();
		command.setSinal(REMESSA_INDEVIDA);
		
		PeticaoStatus novoStatus = PeticaoStatus.A_DEVOLVER;
		command.setStatus(novoStatus.toString());

		processoRestService.sinalizar(id.toLong(), command);
		peticaoRepository.refresh(peticao); // O comando acima poderá alterar o status da petição, por isso o refresh.
		notifyPeticaoStatusModificado(peticao, processoWorkflow.id(), novoStatus);
	}
	
	private void notifyPeticaoStatusModificado(Peticao peticao, ProcessoWorkflowId processoWorkflowId, PeticaoStatus status) {
		eventBus.notify("indexadorEventBus", Event.wrap(new PeticaoStatusModificado(peticao.id(), peticao.getClass().getSimpleName(), processoWorkflowId, status)));
	}
	
}
