package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.DistribuirPeticaoCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto.ProcessoDto;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.facade.ProcessoServiceFacade;

@ActionController(groups = "processo")
public class ProcessoActionsResource {

	@Autowired
	private ProcessoServiceFacade processoServiceFacade;
	
	@ActionMapping(id = "distribuir-processo", name = "Distribuir Processo")
	public ProcessoDto distribuir(DistribuirPeticaoCommand command) {
		return processoServiceFacade.distribuir(command.getTipoDistribuicao(), command.getPeticaoId(), command.getJustificativa(),
				command.getMinistrosCandidatos(), command.getMinistrosImpedidos(), command.getProcessosPreventos());
	}
	
}
