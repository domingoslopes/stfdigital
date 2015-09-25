package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.actions.annotation.ActionController;
import br.jus.stf.plataforma.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.RegistrarPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;

@ActionController
public class PeticaoActionsResource {
	
	@Autowired
	private PeticaoServiceFacade peticaoServiceFacade;

	@ActionMapping(id = "REGISTRAR_PETICAO_ELETRONICA", name = "RegistrarPeticaoEletronica", neededAuthorities = {"peticionador"})
	public Long peticionar(RegistrarPeticaoCommand command) {			
		return peticaoServiceFacade.peticionar(command.getClasseId(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo(), command.getDocumentos());
	}
	
	@ActionMapping(id = "REGISTRAR_PETICAO_FISICA", name = "RegistrarPeticaoFisica", neededAuthorities = {"recebedor"})
	public Long registrar(RegistrarPeticaoFisicaCommand command) {		
    	return peticaoServiceFacade.registrar(command.getQuantidadeVolumes(), command.getQuantidadeApensos(), command.getFormaRecebimento(), command.getNumeroSedex());
	}
	
	@ActionMapping(id = "PREAUTUAR_PETICAO_FISICA", name = "PreautuarPeticaoFisica", neededAuthorities = {"preautuador"})
	public void preautuar(PreautuarPeticaoFisicaCommand command) {
		peticaoServiceFacade.preautuar(command.getPeticaoId(), command.getClasseId()); 
	}
	
	@ActionMapping(id = "AUTUAR_PETICAO", name = "AutuarPeticao", neededAuthorities = {"autuador"})
	public void autuar(AutuarPeticaoCommand command) {
		peticaoServiceFacade.autuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo()); 
	}
}
