package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.RegistrarPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;

/**
 * @author Lucas.Rodrigues
 *
 */
@ActionController(groups = "menu")
public class NovaPeticaoActionsResource {
	
	@Autowired
	private PeticaoServiceFacade peticaoServiceFacade;

	@ActionMapping(id = "registrar-peticao-eletronica", name = "Petição Eletrônica")
	public Long peticionar(RegistrarPeticaoCommand command) {			
		return peticaoServiceFacade.peticionar(command.getClasseId(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo(), command.getPecas(), command.getOrgaoId());
	}
	
	@ActionMapping(id = "registrar-peticao-eletronica-orgao", name = "Petição Órgão")
	public Long peticionarOrgao(RegistrarPeticaoCommand command) {			
		return peticaoServiceFacade.peticionar(command.getClasseId(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo(), command.getPecas(), command.getOrgaoId());
	}
	
	@ActionMapping(id = "registrar-peticao-fisica", name = "Petição Física")
	public Long registrar(RegistrarPeticaoFisicaCommand command) {		
    	return peticaoServiceFacade.registrar(command.getQuantidadeVolumes(), command.getQuantidadeApensos(), command.getFormaRecebimento(), command.getNumeroSedex(), command.getTipoProcesso());
	}
	
}
