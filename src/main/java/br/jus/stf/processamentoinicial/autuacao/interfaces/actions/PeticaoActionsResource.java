package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoStatus;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoDevolucao;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.AssinarDevolucaoPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.DevolverPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;

@ActionController(groups = "peticao")
public class PeticaoActionsResource {
	
	@Autowired
	private PeticaoServiceFacade peticaoServiceFacade;
	
	@ActionMapping(id = "PREAUTUAR", name = "Preautuar Petição Física")
	public void preautuar(PreautuarPeticaoFisicaCommand command) {
		peticaoServiceFacade.preautuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo());
	}
	
	@ActionMapping(id = "AUTUAR", name = "Autuar Petição")
	public void autuar(AutuarPeticaoCommand command) {
		peticaoServiceFacade.autuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo()); 
	}
	
	@ActionMapping(id = "DEVOLVER-PETICAO", name = "Devolver Petição")
	public void devolver(DevolverPeticaoCommand command) {
		TipoDevolucao tipoDevolucao = TipoDevolucao.valueOf(command.getTipoDevolucao());
		peticaoServiceFacade.devolver(command.getPeticaoId(), tipoDevolucao, command.getNumeroOficio()); 
	}
	
	@ActionMapping(id = "ASSINAR-DEVOLUCAO-PETICAO", name = "Assinar Documento de Devolução")
	@FiltrarPeticaoPorStatus(PeticaoStatus.ASSINAR_DEVOLUCAO)
	public void assinar(List<AssinarDevolucaoPeticaoCommand> command) {
		 
	}
	
}
