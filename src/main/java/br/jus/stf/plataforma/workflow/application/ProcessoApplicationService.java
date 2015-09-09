package br.jus.stf.plataforma.workflow.application;

import java.util.List;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import br.jus.stf.plataforma.workflow.domain.ProcessoRepository;

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
	 * Inicia uma nova inãncia do processo de autuação de originários.
	 * @param tipoRecebimento Identificador da forma de ingresso de uma petição (física ou eletrônica).
	 * @return Identificador da instãncia do processo de autuação criado.
	 */
	public String iniciar(String tipoRecebimento) {
		return processoRepository.criar(tipoRecebimento);
	}

	public void alterar(String id, String nome, String valor){
		this.processoRepository.alterar(id, nome, valor);
	}
	
	public ProcessInstance consultar(String id){
		return this.processoRepository.consultar(id);
	}
}
