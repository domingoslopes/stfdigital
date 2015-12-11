package br.jus.stf.plataforma.identidades.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.identidades.interfaces.commands.AssociadoCommand;
import br.jus.stf.plataforma.identidades.interfaces.facade.AssociadoServiceFacade;
import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;

@ActionController(groups = "associado")
public class AssociadoActionResource {
	@Autowired
	private AssociadoServiceFacade associadoServiceFacade;
	
	@ActionMapping(id = "REGISTRAR-ASSOCIADO", name = "Registrar Associado")
	public void registrar(AssociadoCommand command) {
		associadoServiceFacade.cadastrar(command.getIdOrgao(), command.getCPF(), command.getNome(), command.getTipoAssociacao(), command.getCargo());			
	}
}
