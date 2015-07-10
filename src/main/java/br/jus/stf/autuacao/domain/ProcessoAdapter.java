package br.jus.stf.autuacao.domain;

import org.springframework.stereotype.Component;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public interface ProcessoAdapter {

	public String iniciar(String id);

}
