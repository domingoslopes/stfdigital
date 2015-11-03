package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.RegistrarPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;

@ActionController
public class PeticaoActionsResource {
	
	@Autowired
	private PeticaoServiceFacade peticaoServiceFacade;

	@ActionMapping(id = "REGISTRAR_PETICAO_ELETRONICA", name = "Registrar Petição Eletrônica", neededAuthorities = {"peticionador"})
	public Long peticionar(RegistrarPeticaoCommand command) {			
		return peticaoServiceFacade.peticionar(command.getClasseId(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo(), command.getPecas(), command.getOrgaoId());
	}
	
	@ActionMapping(id = "REGISTRAR_PETICAO_ELETRONICA_ORGAO", name = "Registrar Petição Eletrônica", neededAuthorities = {"representante"})
	public Long peticionarOrgao(RegistrarPeticaoCommand command) {			
		return peticaoServiceFacade.peticionar(command.getClasseId(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo(), command.getPecas(), command.getOrgaoId());
	}
	
	@ActionMapping(id = "REGISTRAR_PETICAO_FISICA", name = "Registrar Petição Física", neededAuthorities = {"recebedor"})
	public Long registrar(RegistrarPeticaoFisicaCommand command) {		
    	return peticaoServiceFacade.registrar(command.getQuantidadeVolumes(), command.getQuantidadeApensos(), command.getFormaRecebimento(), command.getNumeroSedex());
	}
	
	@ActionMapping(id = "PREAUTUAR_PETICAO_FISICA", name = "Preautuar Petição Física", neededAuthorities = {"preautuador"})
	public void preautuar(PreautuarPeticaoFisicaCommand command) {
		peticaoServiceFacade.preautuar(null, command.getClasseId(), false, null); 
	}
	
	@ActionMapping(id = "AUTUAR_PETICAO", name = "Autuar Petição", neededAuthorities = {"autuador"})
	public void autuar(AutuarPeticaoCommand command) {
		peticaoServiceFacade.autuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo()); 
	}
}
