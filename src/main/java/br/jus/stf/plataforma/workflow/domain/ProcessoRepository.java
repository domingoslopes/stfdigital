package br.jus.stf.plataforma.workflow.domain;

import org.activiti.engine.runtime.ProcessInstance;

import br.jus.stf.autuacao.domain.entity.Peticao;

/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
public interface ProcessoRepository {
	/**
	 * Cria uma instãncia do processo de autuação de originários.
	 * @param mensagem Nome da mensagem que iniciaráo evento de criação do processo.
	 * @return Identificador da instância do processo de autuação criado.
	 */
	String criar(String mensagem);
	
	void alterar(String id, String nome, String valor);

	ProcessInstance consultar(String id);
}
