package br.jus.stf.plataforma.pesquisas.domain.model.pesquisa;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.UsuarioId;
import br.jus.stf.shared.stereotype.Entity;

/**
 * @author Lucas.Rodrigues
 *
 */
@javax.persistence.Entity
@Table(name = "PESQUISA", schema = "PLATAFORMA")
public class PesquisaAvancada implements Entity<PesquisaAvancada, PesquisaAvancadaId> {

	@EmbeddedId
	private PesquisaAvancadaId id;
	
	@Column(name = "NOM_PESQUISA", nullable = false)
	private String nome;
	
	@Column(name = "TXT_CONSULTA", nullable = false)
	private String consulta;
	
	@Embedded
	private UsuarioId usuario;
	
	public PesquisaAvancada(final PesquisaAvancadaId id, final String nome, final String consulta) {
		Validate.notEmpty(nome, "pesquisaAvancada.nome.required");
		Validate.notEmpty(consulta, "pesquisaAvancada.consulta.required");
		
		this.id = id;
		this.nome = nome;
		this.consulta = consulta;
	}
	
	@Override
	public PesquisaAvancadaId id() {
		return id;
	}
	
	public String nome(){
		return this.nome;
	}
	
	public String consulta() {
		return consulta;
	}
	
	public UsuarioId usuario() {
		return usuario;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		PesquisaAvancada other = (PesquisaAvancada) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(PesquisaAvancada other) {
		return other != null && id.equals(other.id);
	}

}
