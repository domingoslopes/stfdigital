package br.jus.stf.jurisprudencia.controletese.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.AssuntoId;

@Entity
@Table(name = "ASSUNTO", schema = "JURISPRUDENCIA", uniqueConstraints = @UniqueConstraint(columnNames = {"DSC_ASSUNTO"}))
public class Assunto implements br.jus.stf.shared.stereotype.Entity<Assunto, AssuntoId> {
	
	@EmbeddedId
	private AssuntoId codigo;
	
	@Column(name = "DSC_ASSUNTO", nullable = false)
	private String descricao;
	
	Assunto() {
		
	}
	
	public Assunto(final AssuntoId codigo, final String descricao) {
		Validate.notNull(codigo, "assunto.codigo.required");
		Validate.notBlank(descricao, "assunto.descricao.required");
		
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}
	
	@Override
	public AssuntoId id() {
		return this.codigo;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(codigo).hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
	
		Assunto other = (Assunto) obj;
		
		return codigo.equals(other.codigo);
	}

	@Override
	public boolean sameIdentityAs(final Assunto other) {
		return other != null
				&& codigo.equals(other.codigo);
	}

}
