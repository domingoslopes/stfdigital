package br.jus.stf.workflow.infra.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.shared.domain.model.TarefaId;
import br.jus.stf.workflow.domain.model.Tarefa;
import br.jus.stf.workflow.domain.model.TarefaRepository;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Repository
public class TarefaRepositoryImpl implements TarefaRepository {

	@Autowired
	private TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;

	@Override
	public List<Tarefa> listar(String papel) {
		return taskService.createTaskQuery().taskCandidateGroup(papel).includeProcessVariables().list()
				.stream()
				.map(task -> new Tarefa(task))
				.collect(Collectors.toList());
	}

	@Override
	public void completar(Tarefa tarefa, String status) {
		Map<String, Object> variaveis = new HashMap<String, Object>();
		variaveis.put("status", status);
		taskService.complete(tarefa.id().toString(), variaveis);
	}

	@Override
	public Tarefa consultar(TarefaId id) {
		return Optional.ofNullable(
					taskService.createTaskQuery().taskId(id.toString()).singleResult())
				.map(task -> new Tarefa(task))
				.orElse(null);
	}
	
	@Override
	public Tarefa consultarPorProcesso(ProcessoWorkflowId id) {
		return Optional.ofNullable(
					taskService.createTaskQuery().processInstanceId(id.toString()).active().singleResult())
				.map(task -> new Tarefa(task))
				.orElse(null);
	}
}
