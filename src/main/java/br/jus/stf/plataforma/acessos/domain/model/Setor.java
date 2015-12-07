package br.jus.stf.plataforma.acessos.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "SETOR", schema = "CORPORATIVO", uniqueConstraints = {@UniqueConstraint(columnNames = {"NOM_SETOR"}),
		@UniqueConstraint(columnNames = {"SIG_SETOR"})})
public class Setor implements ValueObject<Setor> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COD_SETOR")
	private Long codigo;
	
	@Column(name = "NOM_SETOR", nullable = false)
	private String nome;
	
	@Column(name = "SIG_SETOR", nullable = false)
	private String sigla;
	
	Setor() {
		
	}
	
	public Setor(final Long codigo, final String nome, final String sigla) {
		Validate.notNull(codigo, "setor.sequencial.required");
		Validate.notBlank(nome, "setor.nome.required");
		Validate.notBlank(nome, "setor.sigla.required");
		
		this.codigo = codigo;
		this.nome = nome;
		this.sigla = sigla;
	}
	
	public Long codigo() {
		return codigo;
	}

	public String nome() {
		return nome;
	}
	
	public String sigla() {
		return sigla;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(codigo).append(nome).append(sigla).hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
	
		Setor other = (Setor) obj;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final Setor other) {
		return other != null
				&& codigo.equals(other.codigo)
				&& nome.equals(other.nome)
				&& sigla.equals(other.sigla);
	}

}
