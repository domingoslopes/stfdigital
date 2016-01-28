package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.jurisprudencia.controletese.domain.model.AssuntoRepository;
import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.autuacao.domain.PessoaAdapter;
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
	
	@Autowired
	private AssuntoRepository assuntoRepository;
	
	@Autowired
	private PessoaAdapter pessoaAdapter;
	
	@ActionMapping(id = "preautuar-recursal", name = "Preautuar Petição Física Recursal")
	public void preautuar(PreautuarPeticaoFisicaCommand command) {
		peticaoServiceFacade.preautuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo(), command.getPreferencias());
	}
	
	
	
}
