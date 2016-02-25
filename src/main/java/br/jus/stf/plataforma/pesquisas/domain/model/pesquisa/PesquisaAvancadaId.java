package br.jus.stf.plataforma.pesquisas.domain.model.pesquisa;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;


/**
 * @author Lucas.Rodrigues
 *
 */
@Embeddable
public class PesquisaAvancadaId implements ValueObject<PesquisaAvancadaId> {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SEQ_PESQUISA_AVANCADA", nullable = false)
	private Long sequencial;

	PesquisaAvancadaId() {

	}
	
	public PesquisaAvancadaId(final Long sequencial){
		Validate.notNull(sequencial, "pesquisaId.sequencial.required");
		
		this.sequencial = sequencial;
	}
	
	public Long toLong(){
		return sequencial;
	}
	
	@Override
	public String toString(){
		return sequencial.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sequencial).toHashCode();
	}
	
	@Override
	public boolean equals(final Object o){
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
	
		PesquisaAvancadaId other = (PesquisaAvancadaId) o;
		return sameValueAs(other);
	}

	/**
	 * 
	 * @param other
	 */
	@Override
	public boolean sameValueAs(final PesquisaAvancadaId other){
		return other != null && sequencial.equals(other.sequencial);
	}
	
}
