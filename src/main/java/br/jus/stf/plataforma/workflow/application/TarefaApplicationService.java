package br.jus.stf.plataforma.workflow.application;

import java.util.List;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.workflow.domain.TarefaRepository;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class TarefaApplicationService {

	@Autowired
	private TarefaRepository tarefaRepository;

	public List<Task> tarefas() {
		return tarefaRepository.listar();
	}

	public void completar(String idTarefa) {
		tarefaRepository.completar(idTarefa);
	}

	public void sinalizar(String sinal) {
		tarefaRepository.sinalizar(sinal);
	}

}