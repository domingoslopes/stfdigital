package br.jus.stf.plataforma.acessos.domain.model;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

public class Recurso implements ValueObject<Recurso> {
	
	private static final long serialVersionUID = 1L;
	private String nome;
	private TipoRecurso tipoRecurso;
	private Set<Permissao> permissoesExigidas;
	
	public Recurso(String nome, TipoRecurso tipoRecurso, Set<Permissao> permissoesExigidas) {
		Validate.notBlank(nome, "recurso.nome.required");
		Validate.notNull(tipoRecurso, "recurso.tipoRecurso.required");
		Validate.notEmpty(permissoesExigidas, "recurso.permissoesExigidas.required");
		
		this.nome = nome;
		this.tipoRecurso = tipoRecurso;
		this.permissoesExigidas = permissoesExigidas;
	}
	
	public String nome() {
		return nome;
	}
	
	public TipoRecurso tipoRecurso() {
		return tipoRecurso;
	}
	
	public Set<Permissao> permissoesExigidas() {
		return Collections.unmodifiableSet(permissoesExigidas);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((permissoesExigidas == null) ? 0 : permissoesExigidas.hashCode());
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
	
		Recurso other = (Recurso) obj;
		return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(Recurso other) {
		return other != null && nome.equals(other.nome)
				&& permissoesExigidas.equals(other.permissoesExigidas);
	}

}
