package br.jus.stf.autuacao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.workflow.interfaces.dto.TarefaDto;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public interface TarefaAdapter {

	public void completar(String id);

	public void sinalizar(String sinal, String executionId);
	
	public TarefaDto consultar(String id);

}
