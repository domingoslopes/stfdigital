package br.jus.stf.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.TarefaAdapter;
import br.jus.stf.plataforma.workflow.interfaces.TarefaRestService;
import br.jus.stf.plataforma.workflow.interfaces.commands.CompletarTarefaCommand;
import br.jus.stf.plataforma.workflow.interfaces.commands.SinalizarCommand;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class TarefaRestAdapter implements TarefaAdapter {

	@Autowired
	private TarefaRestService tarefaRestService;

	@Override
	public void completar(String id) {
		CompletarTarefaCommand command = new CompletarTarefaCommand();

		command.setIdTarefa(id);

		// [TODO] Rodrigo Barrerios: Substituir pelo Mecanismo de Integração
		tarefaRestService.completar(command);
	}

	@Override
	public void sinalizar(String sinal) {
		SinalizarCommand command = new SinalizarCommand();

		command.setSinal(sinal);

		// [TODO] Rodrigo Barrerios: Substituir pelo Mecanismo de Integração
		tarefaRestService.sinalizar(command);
	}

}
