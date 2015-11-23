package br.jus.stf.plataforma.acessos.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "TIPO_INFORMACAO", schema = "PLATAFORMA", uniqueConstraints = @UniqueConstraint(columnNames = {"NOM_TIPO_INFORMACAO"}))
public class TipoInformacao implements ValueObject<TipoInformacao> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SEQ_TIPO_INFORMACAO")
	@SequenceGenerator(name = "TIPOINFORMACAOID", sequenceName = "PLATAFORMA.SEQ_TIPO_INFORMACAO", allocationSize = 1)
	@GeneratedValue(generator = "TIPOINFORMACAOID", strategy=GenerationType.SEQUENCE)
	private Long sequencial;
	
	@Column(name = "NOM_TIPO_INFORMACAO", nullable = false)
	private String nome;
	
	TipoInformacao() {
		
	}
	
	public TipoInformacao(final Long sequencial, final String nome) {
		Validate.notNull(sequencial, "tipoInformacao.sequencial.required");
		Validate.notBlank(nome, "tipoInformacao.nome.required");
		
		this.sequencial = sequencial;
		this.nome = nome;
	}
	
	public Long toLong() {
		return this.sequencial;
	}
	
	public String nome() {
		return nome;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((sequencial == null) ? 0 : sequencial.hashCode());
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
	
		TipoInformacao other = (TipoInformacao) obj;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final TipoInformacao other) {
		return other != null && sequencial.equals(other.sequencial);
	}

}
