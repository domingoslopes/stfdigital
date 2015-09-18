package br.jus.stf.autuacao.domain;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.model.Peticao;


/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class PeticaoService {
	
	@Autowired
	private ProcessoAdapter processoAdapter;

	@Autowired
	private TarefaAdapter tarefaAdapter;

	@Deprecated
	public void preautuar(String idPeticao) {
		tarefaAdapter.completar(idPeticao);
	}

	@Deprecated
	public void autuar(String idPeticao, boolean peticaoValida, String motivo) {
		if (peticaoValida) {
			tarefaAdapter.completar(idPeticao);
		} else {
			tarefaAdapter.sinalizar("Petição Inválida", idPeticao);
		}
	}

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Deprecated
	public String distribuir(String idPeticao, String relator) {
		String idProcesso = "";
		
		tarefaAdapter.completar(idPeticao);
		
		return idProcesso;
	}

	@Deprecated
	public void devolver(String idPeticao) {
		tarefaAdapter.completar(idPeticao);
	}

	/**
	 * Recupera uma petição de acordo com o id informado.
	 * @param id - Identificador da petição.
	 * @return Dados da petição.
	 */
	public Peticao consultar(String id){
		return null;
	}
	
}
