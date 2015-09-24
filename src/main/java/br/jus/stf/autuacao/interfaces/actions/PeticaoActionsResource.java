package br.jus.stf.autuacao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.DistribuirPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;
import br.jus.stf.autuacao.interfaces.dto.ProcessoDto;
import br.jus.stf.autuacao.interfaces.facade.PeticaoServiceFacade;
import br.jus.stf.shared.infra.action.annotation.ActionController;
import br.jus.stf.shared.infra.action.annotation.ActionMapping;

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
	
	@ActionMapping(id = "DISTRIBUIR_PETICAO", name = "DistribuirPeticao", neededAuthorities = {"distribuidor"})
	public ProcessoDto distribuir(DistribuirPeticaoCommand command) {
    	return peticaoServiceFacade.distribuir(command.getPeticaoId(), command.getMinistroId());
    }
}
