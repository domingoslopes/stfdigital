package br.jus.stf.workflow.application;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.workflow.domain.model.ProcessoRepository;

/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class ProcessoApplicationService {

	@Autowired
	private ProcessoRepository processoRepository;

	/**
	 * Inicia uma nova instância do processo de autuação de originários.
	 * @param mensagem Identificador da forma de ingresso de uma petição (física ou eletrônica).
	 * @param statusInicial status inicial do processo de workflow
	 * @return Identificador da instãncia do processo de autuação criado.
	 */
	public String iniciar(String mensagem) {
		return processoRepository.criar(mensagem);
	}

	public void alterar(String id, String nome, String valor){
		this.processoRepository.alterar(id, nome, valor);
	}
	
	public ProcessInstance consultar(String id){
		return this.processoRepository.consultar(id);
	}
}
