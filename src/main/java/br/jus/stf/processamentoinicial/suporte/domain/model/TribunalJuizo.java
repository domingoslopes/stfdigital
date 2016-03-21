package br.jus.stf.processamentoinicial.suporte.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 *
 */
@javax.persistence.Entity
@Table(name = "TRIBUNAL_JUIZO", schema = "AUTUACAO")
public class TribunalJuizo implements ValueObject<TribunalJuizo> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COD_TRIBUNAL_JUIZO")
	private Long codigo;
	
	@Column(name = "NOM_TRIBUNAL_JUIZO", nullable = false)
	private String nome;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "TRIBUNAL_JUIZO_ATUACAO", schema = "AUTUACAO",
		joinColumns = @JoinColumn(name = "COD_TRIBUNAL_JUIZO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_UNIDADE_FEDERACAO", nullable = false))
	private Set<UnidadeFederacao> ufsAtuacao = new HashSet<UnidadeFederacao>(0);
	
	TribunalJuizo() {

	}
	
	public TribunalJuizo(final Long codigo, final String nome){
		Validate.notNull(codigo, "unidadeFederacao.codigo.required");
		Validate.notBlank(nome, "unidadeFederacao.nome.required");
		
		this.codigo = codigo;
		this.nome = nome;
	}

	public Long toLong(){
		return codigo;
	}

	public String nome(){
		return nome;
	}
	
	public Set<UnidadeFederacao> ufsAtuacao(){
		return Collections.unmodifiableSet(ufsAtuacao);
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(codigo).hashCode();
	}
	
	@Override
	public boolean equals(final Object o){
		
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
	
		TribunalJuizo other = (TribunalJuizo) o;
		return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(final TribunalJuizo other) {
		return other != null && codigo.equals(other.codigo);
	}
	
}