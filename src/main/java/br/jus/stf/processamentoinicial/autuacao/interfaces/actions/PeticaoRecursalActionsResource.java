package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;

/**
 * @author Anderson.Araujo
 * 
 * @since 18.01.2016
 *
 */
@ActionController(groups="recursal")
public class PeticaoRecursalActionsResource {

	@Autowired
	private PeticaoServiceFacade peticaoServiceFacade;
	
	@ActionMapping(id = "preautuar-recursal", name = "Preautuar Petição Física Recursal")
	public void preautuar(PreautuarPeticaoFisicaCommand command) {
		peticaoServiceFacade.preautuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo());
	}
	
}
