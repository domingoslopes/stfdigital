package br.jus.stf.processamentoinicial.suporte.domain.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 *
 */
@javax.persistence.Entity
@Table(name = "UNIDADE_FEDERACAO", schema = "CORPORATIVO")
public class UnidadeFederacao implements ValueObject<UnidadeFederacao> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SEQ_UNIDADE_FEDERACAO")
	private Long sequencial;
	
	@Column(name = "NOM_UNIDADE_FEDERACAO", nullable = false)
	private String nome;
	
	@Column(name = "SIG_UNIDADE_FEDERACAO", nullable = false)
	private String sigla;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_PAIS", nullable = false)
	private Pais pais;

	UnidadeFederacao() {

	}
	
	public UnidadeFederacao(final Long sequencial, final String nome, final String sigla, final Pais pais){
		Validate.notNull(sequencial, "unidadeFederacao.sequencial.required");
		Validate.notBlank(nome, "unidadeFederacao.nome.required");
		Validate.notBlank(sigla, "unidadeFederacao.sigla.required");
		Validate.notNull(pais, "unidadeFederacao.pais.required");
		
		this.sequencial = sequencial;
		this.nome = nome;
		this.sigla = sigla;
		this.pais = pais;
	}

	public Long toLong(){
		return sequencial;
	}

	public String nome(){
		return nome;
	}
	
	public String sigla() {
		return sigla;
	}
	
	public Pais pais() {
		return pais;
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
	
		UnidadeFederacao other = (UnidadeFederacao) o;
		return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(final UnidadeFederacao other) {
		return other != null && sequencial.equals(other.sequencial);
	}
	
}