package br.jus.stf.processamentoinicial.suporte.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "PREFERENCIA", schema = "AUTUACAO", uniqueConstraints = @UniqueConstraint(columnNames = {"DSC_PREFERENCIA"}))
public class Preferencia implements ValueObject<Preferencia> {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PreferenciaId codigo;
	
	@Column(name = "DSC_PREFERENCIA", nullable = false)
	private String descricao;
	
	Preferencia() {
		
	}
	
	public Preferencia(final PreferenciaId codigo, final String descricao) {
		Validate.notNull(codigo, "preferencia.codigo.required");
		Validate.notBlank(descricao, "preferencia.descricao.required");
		
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}
	
	public PreferenciaId toLong() {
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
	
		Preferencia other = (Preferencia) obj;
		
		return codigo.equals(other.codigo);
	}

	@Override
	public boolean sameValueAs(final Preferencia other) {
		return other != null
				&& codigo.equals(other.codigo);
	}

}
