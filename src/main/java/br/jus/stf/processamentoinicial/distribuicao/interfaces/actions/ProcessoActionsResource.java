package br.jus.stf.processamentoinicial.distribuicao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.commands.DistribuirPeticaoCommand;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.dto.ProcessoDto;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.facade.ProcessoServiceFacade;

@ActionController(groups = "processo")
public class ProcessoActionsResource {

	@Autowired
	private ProcessoServiceFacade processoServiceFacade;
	
	@ActionMapping(id = "DISTRIBUIR-PROCESSO", name = "Distribuir Processo")
	public ProcessoDto distribuir(DistribuirPeticaoCommand command) {
		return processoServiceFacade.distribuir(command.getTipoDistribuicao(), command.getPeticaoId(), command.getJustificativa(),
				command.getMinistrosCandidatos(), command.getMinistrosImpedidos(), command.getProcessosPreventos());
	}
	
}
