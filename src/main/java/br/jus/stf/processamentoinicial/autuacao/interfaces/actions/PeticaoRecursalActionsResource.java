package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;
import br.jus.stf.processamentoinicial.recursaledistribuicao.application.ProcessoApplicationService;

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
	
	@Autowired
	private ProcessoApplicationService processoApplicationService;
	
	@ActionMapping(id = "preautuar-recursal", name = "Preautuar Petição Física Recursal")
	public void preautuar(PreautuarPeticaoFisicaCommand command) {
		peticaoServiceFacade.preautuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo(), command.getPreferencias());
	}
	
	@ActionMapping(id = "autuar-recursal-criminal-eleitoral", name = "Autuar Petição Física Recursal Criminal Eleitoral")
	public void autuarRecursalCriminalEleitoral(AutuarPeticaoCommand command) {
		//ProcessoId processoId = new ProcessoId(command.get)
		//processoApplicationService.autuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo());
	}
	
}
