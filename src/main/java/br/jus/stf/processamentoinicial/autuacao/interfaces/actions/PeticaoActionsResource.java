package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoDevolucao;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.DevolverPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.RegistrarPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;

@ActionController
public class PeticaoActionsResource {
	
	@Autowired
	private PeticaoServiceFacade peticaoServiceFacade;

	@ActionMapping(id = "REGISTRAR-PETICAO-ELETRONICA", name = "Registrar Petição Eletrônica")
	public Long peticionar(RegistrarPeticaoCommand command) {			
		return peticaoServiceFacade.peticionar(command.getClasseId(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo(), command.getPecas(), command.getOrgaoId());
	}
	
	@ActionMapping(id = "REGISTRAR-PETICAO-ELETRONICA-ORGAO", name = "Registrar Petição Eletrônica")
	public Long peticionarOrgao(RegistrarPeticaoCommand command) {			
		return peticaoServiceFacade.peticionar(command.getClasseId(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo(), command.getPecas(), command.getOrgaoId());
	}
	
	@ActionMapping(id = "REGISTRAR-PETICAO-FISICA", name = "Registrar Petição Física")
	public Long registrar(RegistrarPeticaoFisicaCommand command) {		
    	return peticaoServiceFacade.registrar(command.getQuantidadeVolumes(), command.getQuantidadeApensos(), command.getFormaRecebimento(), command.getNumeroSedex());
	}
	
	@ActionMapping(id = "PREAUTUAR-PETICAO-FISICA", name = "Preautuar Petição Física")
	public void preautuar(PreautuarPeticaoFisicaCommand command) {
		peticaoServiceFacade.preautuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo());
	}
	
	@ActionMapping(id = "AUTUAR-PETICAO", name = "Autuar Petição")
	public void autuar(AutuarPeticaoCommand command) {
		peticaoServiceFacade.autuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo()); 
	}
	
	@ActionMapping(id = "DEVOLVER-PETICAO", name = "Devolver Petição")
	public void devolver(DevolverPeticaoCommand command) {
		TipoDevolucao tipoDevolucao = TipoDevolucao.valueOf(command.getTipoDevolucao());
		peticaoServiceFacade.devolver(command.getPeticaoId(), tipoDevolucao, command.getNumeroOficio()); 
	}
}
