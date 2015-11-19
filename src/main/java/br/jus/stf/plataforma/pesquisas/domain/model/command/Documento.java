package br.jus.stf.plataforma.pesquisas.domain.model.command;

import org.elasticsearch.common.lang3.Validate;

import br.jus.stf.shared.stereotype.Entity;

/**
 * @author Lucas.Rodrigues
 *
 */
public class Documento implements Entity<Documento, String> {

	private String id;
	private String tipo;	
	private Indice indice;
	private String objeto;
	
	public Documento(String id, String tipo, Indice indice, String objeto) {
		Validate.notBlank(id, "documento.id.required");
		Validate.notBlank(tipo, "documento.tipo.required");
		Validate.notNull(indice, "documento.indice.required");
		Validate.notBlank(objeto, "documento.objeto.required");
		
		this.id = id;
		this.tipo = tipo;
		this.indice = indice;		
		this.objeto = objeto;
	}
	
	@Override
	public String id() {
		return id;
	}
	
	public String tipo() {
		return tipo;
	}
	
	public Indice indice() {
		return indice;
	}
	
	public String objeto() {
		return objeto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		Documento other = (Documento) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(Documento other) {
		return other != null && id.equals(other.id) &&
				tipo.equals(other.tipo) && indice.equals(other.indice) &&
				objeto.equals(other.objeto);
	}	
	
}
