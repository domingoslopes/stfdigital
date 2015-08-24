package br.jus.stf.generico.domain.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
@javax.persistence.Entity
@Table(name = "MINISTRO")
@SequenceGenerator(name = "MINISTROID", sequenceName = "SEQ_MINISTRO", allocationSize = 1, initialValue = 1)
public class Ministro implements Entity<Ministro> {

	@Embedded
	@AttributeOverride(name = "id",
			column = @Column(name = "SEQ_MINISTRO", insertable = false, updatable = false))
	private MinistroId codigo;
	
	@Column(name = "NOM_MINISTRO", nullable = false)
	private String nome;

	public Ministro(final MinistroId codigo, final String nome){
		Validate.notNull(codigo);
		Validate.notBlank(nome);
		
		this.codigo = codigo;
		this.nome = nome;
	}

	public MinistroId codigo(){
		return codigo;
	}

	public String nome(){
		return nome;
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
	
		Ministro other = (Ministro) o;
		return sameIdentityAs(other);
	}
	
	public boolean sameIdentityAs(final Ministro other) {
		return other != null && this.codigo.sameValueAs(other.codigo);
	}

	//Hibernate
	
	@Id
	@Column(name = "SEQ_MINISTRO")
	@GeneratedValue(generator = "MINISTROID", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	Ministro() {
		
	}
}