package br.jus.stf.core.shared.processo;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 27.02.2016
 */
public class Identificacao extends ValueObjectSupport<Identificacao> {
	
	private String classe;
	
	private Long numero;

	public Identificacao(String classe, Long numero) {
		this.classe = classe;
		this.numero = numero;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s", classe, numero);
	}

}
