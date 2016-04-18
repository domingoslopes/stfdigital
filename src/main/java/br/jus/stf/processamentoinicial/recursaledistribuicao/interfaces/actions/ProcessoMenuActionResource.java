package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.actions;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.plataforma.shared.actions.support.ResourcesMode;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.SalvarProcessoEnvioCommand;

/**
 * Controlador de ações relacioandas ao envio de processos.
 * 
 * @author Anderson.Araujo
 * @since 29.03.2016
 *
 */
@ActionController(groups="menu")
public class ProcessoMenuActionResource {
	
	@ActionMapping(id="consultar-processo-envio", name="Enviar Processo", resourcesMode = ResourcesMode.None)
	public void consultarProcessoEnvio(SalvarProcessoEnvioCommand command){
		
	}
}
