package br.jus.stf.plataforma.workflow.infra.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.shared.security.AcessosRestAdapter;
import br.jus.stf.plataforma.shared.security.SecurityContextUtil;
import br.jus.stf.plataforma.workflow.domain.model.Metadado;
import br.jus.stf.plataforma.workflow.domain.model.Responsavel;
import br.jus.stf.plataforma.workflow.domain.model.Tarefa;
import br.jus.stf.plataforma.workflow.domain.model.TarefaRepository;
import br.jus.stf.shared.ProcessoWorkflowId;
import br.jus.stf.shared.TarefaId;
import br.jus.stf.shared.UsuarioId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Repository
public class TarefaRepositoryImpl implements TarefaRepository {

	@Autowired
	private AcessosRestAdapter acessosRestAdapter;
	
	@Autowired
	private TaskService taskService;

	@Override
	public List<Tarefa> listar() {
		Set<String> papeis = acessosRestAdapter.carregarPapeisUsuario(SecurityContextUtil.getUsername());
		return taskService.createTaskQuery().taskCandidateGroupIn(new ArrayList<String>(papeis)).includeProcessVariables().list()
				.stream()
				.map(task -> newTarefa(task))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<Tarefa> listar(Responsavel responsavel) {
		String usuarioId = responsavel.usuarioId().toString();
		return taskService.createTaskQuery().taskAssignee(usuarioId).includeProcessVariables().list()
				.stream()
				.map(task -> newTarefa(task))
				.collect(Collectors.toList());
	}

	@Override
	public void completar(Tarefa tarefa, Metadado metadado) {
		Map<String, Object> variaveis = new HashMap<String, Object>();
		variaveis.put("status", metadado.status());
		Optional.ofNullable(metadado.descricao()).ifPresent(d -> variaveis.put("descricao", d));
		taskService.complete(tarefa.id().toString(), variaveis);
	}

	@Override
	public Tarefa consultar(TarefaId id) {
		return Optional.ofNullable(
					taskService.createTaskQuery().taskId(id.toString()).includeProcessVariables().singleResult())
				.map(task -> newTarefa(task))
				.orElse(null);
	}
	
	@Override
	public Tarefa consultarPorProcesso(ProcessoWorkflowId id) {
		return Optional.ofNullable(
					taskService.createTaskQuery().processInstanceId(id.toString()).includeProcessVariables().singleResult())
				.map(task -> newTarefa(task))
				.orElse(null);
	}
	
	private Tarefa newTarefa(Task task) {
		TarefaId id = new TarefaId(Long.parseLong(task.getId()));
		ProcessoWorkflowId processo = new ProcessoWorkflowId(Long.parseLong(task.getProcessInstanceId()));
		String nome = task.getTaskDefinitionKey();
		String descricao = task.getName();
		Metadado metadado = Metadado.converte(task.getProcessVariables());
		Tarefa tarefa = new Tarefa(id, nome, descricao, processo, metadado);
		Optional.ofNullable(task.getAssignee()).ifPresent(reponsavelId -> tarefa.atribuir(newResponsavel(reponsavelId)));
		return tarefa;
	}
	
	private Responsavel newResponsavel(String id) {
		UsuarioId usuarioId = new UsuarioId(Long.valueOf(id));
		String login = acessosRestAdapter.recuperarNomeUsuario(usuarioId);
		return new Responsavel(usuarioId, login);
	}
	
}
