package br.jus.stf.plataforma.workflow.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.plataforma.shared.actions.support.ResourcesMode;
import br.jus.stf.plataforma.workflow.application.TarefaApplicationService;
import br.jus.stf.plataforma.workflow.interfaces.commands.AssumirTarefaCommand;
import br.jus.stf.plataforma.workflow.interfaces.commands.DelegarTarefaCommand;
import br.jus.stf.shared.TarefaId;
import br.jus.stf.shared.UsuarioId;

/**
 * @author Lucas.Rodrigues
 *
 */
@ActionController(groups = "tarefa")
public class TarefaActionResource {

	@Autowired
	private TarefaApplicationService tarefaApplicationService;
	
	@ActionMapping(id = "assumir-tarefa", name = "Assumir Tarefa", resourcesMode = ResourcesMode.OneOrMany)
	public void assumir(List<AssumirTarefaCommand> commands) {
		commands.stream().forEach(cmd -> {
			TarefaId tarefaId = new TarefaId(cmd.getTarefaId());
			tarefaApplicationService.assumir(tarefaId);
		});
	}
	
	@ActionMapping(id = "delegar-tarefa", name = "Delegar Tarefa", resourcesMode = ResourcesMode.One)
	public void delegar(DelegarTarefaCommand command) {
		TarefaId tarefaId = new TarefaId(command.getTarefaId());
		UsuarioId usuarioId = new UsuarioId(command.getUsuarioId());
		tarefaApplicationService.delegar(tarefaId, usuarioId);
	}
	
}
