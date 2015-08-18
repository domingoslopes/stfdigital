package br.jus.stf.autuacao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.entity.Peticao;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public interface ProcessoAdapter {

	public String iniciar(String id, Peticao peticao);
	
	public void alterar(String idPeticao, String classe);

}
