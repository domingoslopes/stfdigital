package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.autuacao.application.RecursalApplicationService;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.ConfirmarDevolucaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.DevolverPeticaoRecursalCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;

/**
 * @author Anderson.Araujo
 * 
 * @since 18.01.2016
 *
 */
@ActionController(groups="recursal")
public class PeticaoRecursalActionsResource {

	@Autowired
	private RecursalApplicationService recursalApplicationService;
	
	@ActionMapping(id = "preautuar-recursal", name = "Preautuar Petição Física Recursal")
	public void preautuar(PreautuarPeticaoFisicaCommand command) {
		recursalApplicationService.preautuar(command.getPeticaoId(), command.getClasseId());
	}
	
	@ActionMapping(id = "devolver-peticao-recursal", name = "Devolver Petição Recursal")
	public void devolver(DevolverPeticaoRecursalCommand command) {
		recursalApplicationService.devolver(command.getPeticaoId(), command.getMotivo()); 
	}
	
	@ActionMapping(id = "finalizar-devolucao-recursal", name = "Finalizar Devolução Recursal")
	public void devolver(ConfirmarDevolucaoCommand command) {
		recursalApplicationService.confirmarDevolucao(command.getPeticaoId(), command.getTipoDevolucao(), command.getNumeroOficio()); 
	}
}
