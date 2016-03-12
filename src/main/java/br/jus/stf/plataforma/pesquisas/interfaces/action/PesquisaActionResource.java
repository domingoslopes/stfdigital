package br.jus.stf.plataforma.pesquisas.interfaces.action;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.pesquisas.application.PesquisaApplicationService;
import br.jus.stf.plataforma.pesquisas.interfaces.command.SalvarPesquisaAvancadaCommand;
import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.plataforma.shared.actions.support.ResourcesMode;

/**
 * @author Lucas.Rodrigues
 *
 */
@ActionController(groups = "pesquisa")
public class PesquisaActionResource {
	
	@Autowired
	private PesquisaApplicationService pesquisaApplicationService;

	@ActionMapping(id = "salvar-pesquisa-avancada", name = "Salvar pesquisa", resourcesMode = ResourcesMode.One)
	public void salvar(SalvarPesquisaAvancadaCommand command) {
		pesquisaApplicationService.salvar(command.getPesquisaId(), command.getNome(), command.getConsulta(), command.getIndices());
	}
	
}
