package br.jus.stf.processamentoinicial.suporte.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.plataforma.shared.actions.support.ResourcesMode;
import br.jus.stf.processamentoinicial.suporte.interfaces.commands.GerarTextoPeticaoCommand;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.TextoDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.facade.TextoServiceFacade;

/**
 * Ações de textos.
 * 
 * @author Tomas.Godoi
 *
 */
@ActionController(groups = "texto")
public class TextoActionsResource {

	@Autowired
	private TextoServiceFacade textoServiceFacade;

	@ActionMapping(id = "gerar-texto", name = "Gerar Texto", resourcesMode = ResourcesMode.None)
	public TextoDto gerarTextoDePeticao(GerarTextoPeticaoCommand command) {
		return textoServiceFacade.gerarTextoDePeticao(command.getPeticaoId(), command.getModeloId(),
		        command.getSubstituicoes());
	}

}
