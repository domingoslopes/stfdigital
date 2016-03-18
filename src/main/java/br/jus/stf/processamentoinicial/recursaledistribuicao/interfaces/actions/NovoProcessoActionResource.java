package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.actions;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.plataforma.shared.actions.support.ResourcesMode;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.EnviarProcessoCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.SalvarProcessoEnvioCommand;

/**
 * Controlador de ações relacioandas ao envio de processos.
 * 
 * @author Anderson.Araujo
 * @since 18.03.2016
 *
 */
@ActionController(groups="enviaprocesso")
public class NovoProcessoActionResource {
	@ActionMapping(id="salvar-processo", name="Salvar Processo", resourcesMode = ResourcesMode.One)
	public void salvarProcesso(SalvarProcessoEnvioCommand command){
		
	}
	
	@ActionMapping(id="enviar-processo", name="Enviar Processo", resourcesMode = ResourcesMode.One)
	public void enviarProcesso(EnviarProcessoCommand command){
		
	}
}
