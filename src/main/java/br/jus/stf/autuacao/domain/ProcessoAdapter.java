package br.jus.stf.autuacao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.entity.Peticao;

/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public interface ProcessoAdapter {

	/**
	 * Iniciar uma nova instância do processo de autuação de originários.
	 * @param tipoRecebimento Forma como a petição ingressou no Tribunal (física ou eletrônica).
	 * @return Identificador da instância do processo de autuação criado.
	 */
	public String iniciar(String tipoRecebimento);
	
	public void alterar(String id, String nome, String valor);
	
	public Peticao consultar(String id);
}
