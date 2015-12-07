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
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoStatus;
import br.jus.stf.shared.ProcessoWorkflowId;

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

	@Override
	public void iniciarWorkflow(PeticaoEletronica peticaoEletronica) {
		IniciarProcessoCommand command = new IniciarProcessoCommand();
		command.setMensagem("autuarOriginarios");
		command.setStatus(PeticaoStatus.A_AUTUAR.toString());
		command.setInformacao(peticaoEletronica.id().toLong());
		command.setTipoInformacao(peticaoEletronica.getClass().getSimpleName());
		command.setDescricao(peticaoEletronica.identificacao());
		
		Long id = processoRestService.iniciar(command);
		peticaoEletronica.associarProcessoWorkflow(new ProcessoWorkflowId(id));
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
		peticaoFisica.associarProcessoWorkflow(new ProcessoWorkflowId(id));
	}
	
	@Override
	public void rejeitarAutuacao(Peticao peticao) {
		ProcessoWorkflowId id = peticao.processosWorkflow().iterator().next();
		SinalizarCommand command = new SinalizarCommand();
		command.setSinal(PETICAO_INVALIDA);
		command.setStatus(PeticaoStatus.REJEITADA.toString());

		processoRestService.sinalizar(id.toLong(), command);
	}
	
	@Override
	public void devolver(Peticao peticao) {
		ProcessoWorkflowId id = peticao.processosWorkflow().iterator().next();
		SinalizarCommand command = new SinalizarCommand();
		command.setSinal(REMESSA_INDEVIDA);
		command.setStatus(PeticaoStatus.A_DEVOLVER.toString());

		processoRestService.sinalizar(id.toLong(), command);
	}
	
}
