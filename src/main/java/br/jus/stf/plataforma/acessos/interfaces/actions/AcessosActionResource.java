package br.jus.stf.plataforma.acessos.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import br.jus.stf.plataforma.acessos.application.AcessosApplicationService;
import br.jus.stf.plataforma.acessos.interfaces.commands.ConfigurarPermissoesUsuarioCommand;
import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;

@ActionController(groups = "permissao")
public class AcessosActionResource {
	@Autowired
	private AcessosApplicationService acessosApplicationService;
	
	@ActionMapping(id = "CONFIGURAR-PERMISSAO", name = "Configurar permissões de um usuário")
	public void configurarPermissoesUsuario(@RequestBody ConfigurarPermissoesUsuarioCommand command ){
		this.acessosApplicationService.configurarPermissoesUsuario(command.getIdUsuario(), command.getPapeisAdicionados(), 
				command.getGruposAdicionados(), command.getPapeisRemovidos(), command.getGruposRemovidos());
	}
}
