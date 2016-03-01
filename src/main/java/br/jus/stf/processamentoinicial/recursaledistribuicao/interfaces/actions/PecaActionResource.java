package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.plataforma.shared.actions.support.ResourcesMode;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.DividirPecaCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.EditarPecaCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.ExcluirPecasCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.JuntarPecaCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.OrganizarPecasCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.UnirPecasCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.facade.ProcessoServiceFacade;

/**
 * Controle de ações de peças processuais.
 * 
 * @author Anderson.Araujo
 * @since 26.02.2016
 *
 */
@ActionController(groups = "peca")
public class PecaActionResource {
	
	@Autowired
	private ProcessoServiceFacade processoServiceFacade;
	
	@ActionMapping(id = "excluir-pecas", name = "Excluir Peças Processuais", resourcesMode = ResourcesMode.OneOrMany)
	public void excluirPecas(ExcluirPecasCommand command) {
		processoServiceFacade.excluirPecas(command.getProcessoId(), command.getPecas());
	}
	
	@ActionMapping(id = "organizar-pecas", name = "Organizar Peças", resourcesMode = ResourcesMode.OneOrMany)
	public void organizarPecas(OrganizarPecasCommand command) {
		processoServiceFacade.organizarPecas(command.getProcessoId(), command.getPecasOrganizadas(), command.isConcluirTarefa());
	}
	
	@ActionMapping(id = "dividir-peca", name = "Dividir Peça Processual", resourcesMode = ResourcesMode.One)
	public void dividirPeca(DividirPecaCommand command) {
		processoServiceFacade.dividirPeca(command.getProcessoId(), command.getPecaId(), command.getPecas());
	}
	
	@ActionMapping(id = "unir-pecas", name = "Unir Peças Processuais", resourcesMode = ResourcesMode.Many)
	public void unirPecas(UnirPecasCommand command) {
		processoServiceFacade.unirPecas(command.getProcessoId(), command.getPecas());
	}
	
	@ActionMapping(id = "editar-peca", name = "Editar Peça Processual", resourcesMode = ResourcesMode.One)
	public void editarPeca(EditarPecaCommand command) {
		processoServiceFacade.editarPeca(command.getPecaId(), command.getTipoPecaId(), command.getDescricao(), 
				command.getNumeroOrdem(), command.getVisibilidade());
	}
	
	@ActionMapping(id = "juntar-peca", name = "Juntar Peça Processual", resourcesMode = ResourcesMode.One)
	public void juntarPeca(JuntarPecaCommand command) {
		processoServiceFacade.juntarPeca(command.getProcessoId(), command.getPecaId());
	}
}
