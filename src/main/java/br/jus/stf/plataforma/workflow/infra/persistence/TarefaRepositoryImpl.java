package br.jus.stf.plataforma.workflow.infra.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.shared.security.AcessosRestAdapter;
import br.jus.stf.plataforma.shared.security.SecurityContextUtil;
import br.jus.stf.plataforma.shared.security.UserDetails;
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
	public List<Tarefa> listarMinhas() {
		UsuarioId usuarioId = userDetails().getUsuarioId();
		return listarPor(usuarioId);
	}

	@Override
	public List<Tarefa> listarPorMeusPapeis() {
		List<String> papeis = userDetails().getPapeis();
		StringBuilder sql = new StringBuilder("SELECT task.* FROM ACT_RU_TASK task");
		sql.append(" JOIN ACT_RU_IDENTITYLINK link ON task.ID_ = link.TASK_ID_");
		sql.append(" WHERE link.GROUP_ID_ IN(");
		IntStream.range(0, papeis.size())
				 .mapToObj(i -> "'" + papeis.get(i) + "'" + (i < papeis.size()-1 ? "," : ""))
				 .forEach(sql::append);
		sql.append(")");
		
		return taskService.createNativeTaskQuery().sql(sql.toString()).list()
				.stream()
				.map(task -> newTarefa(task, taskService.getVariables(task.getId())))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<Tarefa> listarPor(UsuarioId usuarioId) {
		return taskService.createTaskQuery().taskAssignee(usuarioId.toString()).includeProcessVariables().list()
				.stream()
				.map(task -> newTarefa(task, task.getProcessVariables()))
				.collect(Collectors.toList());
	}

	@Override
	public void completar(Tarefa tarefa, Metadado metadado) {
		if (isUserAssignee(tarefa)) {
			Map<String, Object> variaveis = new HashMap<String, Object>();
			variaveis.put("status", metadado.status());
			Optional.ofNullable(metadado.descricao()).ifPresent(d -> variaveis.put("descricao", d));
			
			taskService.complete(tarefa.id().toString(), variaveis);
		} else {
			throw new IllegalArgumentException("A tarefa não pode ser completada pelo usuário.");
		}
	}

	@Override
	public Tarefa consultar(TarefaId id) {
		return Optional.ofNullable(
					taskService.createTaskQuery().taskId(id.toString()).includeProcessVariables().singleResult())
				.map(task -> newTarefa(task, task.getProcessVariables()))
				.orElse(null);
	}
	
	@Override
	public Tarefa consultarPor(ProcessoWorkflowId id) {
		return Optional.ofNullable(
					taskService.createTaskQuery().processInstanceId(id.toString()).includeProcessVariables().singleResult())
				.map(task -> newTarefa(task, task.getProcessVariables()))
				.orElse(null);
	}
	
	@Override
	public void delegar(TarefaId tarefaId, UsuarioId usuarioId) {
		Validate.notNull(tarefaId);
		Validate.notNull(usuarioId);
		
		Task task = taskService.createTaskQuery().taskId(tarefaId.toString()).singleResult();
		if (task != null) {
			taskService.setAssignee(task.getId(), usuarioId.toString());
		} else {
			throw new IllegalArgumentException("Tarefa inexistente ou usuário não possui os papéis para acesso.");
		}
	}
	
	/**
	 * Cria uma tarefa a partir de uma task do activiti
	 * 
	 * @param task
	 * @param variables
	 * @return
	 */
	private Tarefa newTarefa(Task task, Map<String, Object> variables) {
		TarefaId id = new TarefaId(Long.parseLong(task.getId()));
		ProcessoWorkflowId processo = new ProcessoWorkflowId(Long.parseLong(task.getProcessInstanceId()));
		String nome = task.getTaskDefinitionKey();
		String descricao = task.getName();
		Metadado metadado = Metadado.converte(variables);
		Tarefa tarefa = new Tarefa(id, nome, descricao, processo, metadado);
		Optional.ofNullable(task.getAssignee()).ifPresent(usuarioId -> tarefa.atribuir(newResponsavel(usuarioId)));
		return tarefa;
	}
	
	/**
	 * Cria o responsavel a partir de um usuário
	 * 
	 * @param usuarioId
	 * @return
	 */
	private Responsavel newResponsavel(String usuarioId) {
		UsuarioId id = new UsuarioId(Long.valueOf(usuarioId));
		Optional<UserDetails> userDetails = acessosRestAdapter.recuperarUsuario(id);
		if (userDetails.isPresent()) {
			String nome = userDetails.get().getNome();
			return new Responsavel(id, nome);
		}
		return null;
	}
	
	/**
	 * @return os detalhes do usuário logado
	 */
	private UserDetails userDetails() {
		return SecurityContextUtil.getUser().getUserDetails();
	}
	
	/**
	 * @param tarefa
	 * @return se o usuário é o responsável pela tarefa
	 */
	private boolean isUserAssignee(Tarefa tarefa) {
		return tarefa.reponsavel().usuarioId().sameValueAs(userDetails().getUsuarioId());
	}
	
}
