package br.jus.stf.processamentoinicial.distribuicao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.commands.DistribuirPeticaoCommand;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.dto.ProcessoDto;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.facade.ProcessoServiceFacade;

public class ProcessoActionsResource {

	@Autowired
	private ProcessoServiceFacade processoServiceFacade;
	
	@ActionMapping(id = "DISTRIBUIR_PETICAO", name = "DistribuirPeticao", neededAuthorities = {"distribuidor"})
	public ProcessoDto distribuir(DistribuirPeticaoCommand command) {
		return processoServiceFacade.distribuir(command.getPeticaoId(), command.getMinistroId());
	}

}
