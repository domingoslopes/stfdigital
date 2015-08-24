package br.jus.stf.autuacao.domain.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
@Embeddable
public class ProcessoId implements ValueObject<ProcessoId> {

	private static final long serialVersionUID = 1L;
	
	@Embedded
	@AttributeOverride(name = "sigla",
		column = @Column(name = "SIG_CLASSE", insertable = true, updatable = false, nullable = false))
	private ClasseId classe;
	
	@Column(name = "NUM_PROCESSO", insertable = true, updatable = false, nullable = false)
	private Long numero;

	public ProcessoId(final ClasseId classe, final Long numero){
		this.classe = classe;
		this.numero = numero;
	}

	public ClasseId classe(){
		return classe;
	}
	
	public Long numero(){
		return numero;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classe == null) ? 0 : classe.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		ProcessoId other = (ProcessoId) obj;
		return sameValueAs(other);
	}
	
	/**
	 * 
	 * @param other
	 */
	public boolean sameValueAs(final ProcessoId other){
		return other != null && this.numero.equals(other.numero)
				&& this.classe.equals(other.classe);
	}

	public String toString(){
		return new StringBuilder()
			.append(classe)
			.append(" ")
			.append(numero)
			.toString();
	}

}