package br.jus.stf.plataforma.workflow.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.jus.stf.plataforma.workflow.application.ProcessoApplicationService;
import br.jus.stf.plataforma.workflow.interfaces.commands.IniciarProcessoCommand;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@Component
public class ProcessoRestService {

	@Autowired
	private ProcessoApplicationService processoApplicationService;

	@RequestMapping(value = "/api/processo", method = RequestMethod.POST)
	public String iniciar(@RequestBody IniciarProcessoCommand command) {
		return processoApplicationService.iniciar(command.getIdProcesso());
	}

}
