package br.jus.stf.plataforma.workflow.domain.model;

import java.util.List;

import br.jus.stf.shared.ProcessoWorkflowId;
import br.jus.stf.shared.TarefaId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
public interface TarefaRepository {

	/**
	 * Completa uma tarefa informando um status
	 * 
	 * @param tarefa
	 * @param metadado
	 */
	void completar(Tarefa tarefa, Metadado metadado);

	/**
	 * Lista as tarefas de um papel
	 * 
	 * @param papel
	 * @return
	 */
	List<Tarefa> listar(String papel);
	
	/**
	 * Consulta uma tarefa
	 * 
	 * @param id
	 * @return
	 */
	Tarefa consultar(TarefaId id);

	/**
	 * Consulta a última tarefa por processo
	 * 
	 * @param id
	 * @return
	 */
	Tarefa consultarPorProcesso(ProcessoWorkflowId id);
}
