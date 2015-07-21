package br.jus.stf.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.ProcessoAdapter;
import br.jus.stf.plataforma.workflow.interfaces.ProcessoRestResource;
import br.jus.stf.plataforma.workflow.interfaces.commands.IniciarProcessoCommand;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class ProcessoRestAdapter implements ProcessoAdapter {

	@Autowired
	private ProcessoRestResource processoRestService;

	@Override
	public String iniciar(String idProcesso) {
		IniciarProcessoCommand command = new IniciarProcessoCommand();

		command.setIdProcesso(idProcesso);

		// [TODO] Rodrigo Barrerios: Substituir pelo Mecanismo de Integração
		return processoRestService.iniciar(command);
	}

}
