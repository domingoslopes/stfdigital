package br.jus.stf.core.identificadores;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 27.02.2016
 */
@Entity
public class Identificador extends EntitySupport<Identificador, String> {
	
	@Id
	private String categoria;
	
	@Column
	private Long numero;
	
	public Identificador() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}

	public Identificador(String categoria, Long numero) {
		this.categoria = categoria;
		this.numero = numero;
	}

	@Override
	public String identity() {
		return categoria;
	}
	
	public Long numero() {
		return numero;
	}

	public void incrementar() {
		++numero;
	}
	
}
