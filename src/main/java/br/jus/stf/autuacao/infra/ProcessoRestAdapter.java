package br.jus.stf.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.ProcessoAdapter;
import br.jus.stf.autuacao.domain.model.Peticao;
import br.jus.stf.workflow.interfaces.ProcessoRestResource;
import br.jus.stf.workflow.interfaces.commands.IniciarProcessoCommand;

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
	public String iniciar(String tipoRecebimento) {
		
		IniciarProcessoCommand command = new IniciarProcessoCommand();
		command.setMensagem(tipoRecebimento);
		
		return processoRestService.iniciar(command);
	}
}
