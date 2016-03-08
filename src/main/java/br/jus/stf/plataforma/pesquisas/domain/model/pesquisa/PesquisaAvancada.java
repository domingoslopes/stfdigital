package br.jus.stf.plataforma.pesquisas.domain.model.pesquisa;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.plataforma.shared.security.SecurityContextUtil;
import br.jus.stf.shared.UsuarioId;
import br.jus.stf.shared.stereotype.Entity;

/**
 * @author Lucas.Rodrigues
 *
 */
@javax.persistence.Entity
@Table(name = "PESQUISA_AVANCADA", schema = "PLATAFORMA")
public class PesquisaAvancada implements Entity<PesquisaAvancada, PesquisaAvancadaId> {

	@EmbeddedId
	private PesquisaAvancadaId id;
	
	@Column(name = "NOM_PESQUISA", nullable = false)
	private String nome;
	
	@Column(name = "TXT_CONSULTA", nullable = false)
	@Lob
	private String consulta;
	
	@Column(name = "TXT_INDICES", nullable = false)
	private String indices;
	
	@Embedded
	private UsuarioId usuario;
	
	@Transient
	private String[] indicesArray;
	
	PesquisaAvancada() { }
	
	public PesquisaAvancada(final PesquisaAvancadaId id, final String nome, final String consulta, final String[] indices) {
		Validate.notNull(id, "pesquisaAvancada.id.required");
		Validate.notEmpty(nome, "pesquisaAvancada.nome.required");
		Validate.notEmpty(consulta, "pesquisaAvancada.consulta.required");
		Validate.notEmpty(indices, "pesquisaAvancada.indices.required");
		
		this.id = id;
		this.nome = nome;
		this.consulta = consulta;
		this.indicesArray = indices;
		this.indices = Arrays.toString(indices);
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
	
	public String[] indices() {
		if (indicesArray == null) {
			indicesArray = indices.replace("[", "").replace("]", "").split(",");
		}
		return indicesArray;
	}; 

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
	
	@PrePersist
	@PreUpdate
	private void carregarUsuario() {
		usuario = SecurityContextUtil.getUser().getUserDetails().getUsuarioId();
	}

}
