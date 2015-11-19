package br.jus.stf.plataforma.acessos.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.Validate;

@MappedSuperclass
public abstract class Principal {

	private String nome;
	
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Permissao> permissoes = new HashSet<Permissao>(0);
	
	public Principal(final String nome) {
		Validate.notBlank(nome, "principal.segmento.required");
		
		this.nome = nome;
	}
	
	public String nome() {
		return nome;
	}
	
	public void atribuirPermissoes(final Set<Permissao> permissoes) {
		Validate.notNull(permissoes, "principal.segmento.required");
		
		this.permissoes = permissoes;
	}
	
	public boolean possuiAcessoNo(Recurso recurso) {
		Set<Permissao> intersecao = new HashSet<Permissao>(permissoes);
		Set<Permissao> permissoesExigidas = recurso.permissoesExigidas();
		
		intersecao.retainAll(permissoesExigidas);
		
		return permissoesExigidas.equals(intersecao);
	}
	
}
