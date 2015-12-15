package br.jus.stf.plataforma.identidades.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.identidades.interfaces.commands.AssociadoCommand;
import br.jus.stf.plataforma.identidades.interfaces.facade.AssociadoServiceFacade;
import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;

@ActionController(groups = "menu")
public class AssociadoActionsResource {
	@Autowired
	private AssociadoServiceFacade associadoServiceFacade;
	
	@ActionMapping(id = "registrar-associado", name = "Registrar Associado")
	public void registrar(AssociadoCommand command) {
		associadoServiceFacade.cadastrar(command.getIdOrgao(), command.getCPF(), command.getNome(), command.getTipoAssociacao(), command.getCargo());			
	}
}
