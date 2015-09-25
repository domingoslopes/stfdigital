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

	@Override
	public void iniciarProcessoWorkflow(PeticaoEletronica peticaoEletronica) {
		iniciarProcessoWorkflow(peticaoEletronica, "remessaEletronica", PeticaoStatus.A_AUTUAR);
	}

	@Override
	public void iniciarProcessoWorkflow(PeticaoFisica peticaoFisica) {
		iniciarProcessoWorkflow(peticaoFisica, "remessaFisica", PeticaoStatus.A_PREAUTUAR);
	}
	
	@Override
	public void rejeitarAutuacao(Peticao peticao) {
		ProcessoWorkflowId id = peticao.processosWorkflow().iterator().next();
		SinalizarCommand command = new SinalizarCommand();
		command.setSinal("peticaoInvalida");
		command.setStatus(PeticaoStatus.REJEITADA.toString());

		// [TODO] Rodrigo Barrerios: Substituir pelo Mecanismo de Integração
		processoRestService.sinalizar(id.toLong(), command);
	}
	
	/**
	 * Inicia um processo do workflow
	 * 
	 * @param peticao
	 * @param mensagem
	 * @param statusInicial
	 */
	private void iniciarProcessoWorkflow(Peticao peticao, String mensagem, PeticaoStatus statusInicial) {
		IniciarProcessoCommand command = new IniciarProcessoCommand();
		command.setMensagem(mensagem);
		command.setStatus(statusInicial.toString());
		command.setInformacao(peticao.id().toLong());
		
		Long id = processoRestService.iniciar(command);
		peticao.associarProcessoWorkflow(new ProcessoWorkflowId(id));
	}
	
}
