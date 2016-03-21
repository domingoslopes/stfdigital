package br.jus.stf.processamentoinicial.suporte.domain.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 *
 */
@javax.persistence.Entity
@Table(name = "PAIS", schema = "CORPORATIVO")
public class Pais implements ValueObject<Pais> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SEQ_PAIS")
	private Long sequencial;
	
	@Column(name = "NOM_PAIS", nullable = false)
	private String nome;

	Pais() {

	}
	
	public Pais(final Long sequencial, final String nome){
		Validate.notNull(sequencial, "unidadeFederacao.sequencial.required");
		Validate.notBlank(nome, "unidadeFederacao.nome.required");
		
		this.sequencial = sequencial;
		this.nome = nome;
	}

	public Long toLong(){
		return sequencial;
	}

	public String nome(){
		return nome;
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(sequencial).hashCode();
	}
	
	@Override
	public boolean equals(final Object o){
		
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
	
		Pais other = (Pais) o;
		return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(final Pais other) {
		return other != null && sequencial.equals(other.sequencial);
	}
	
}