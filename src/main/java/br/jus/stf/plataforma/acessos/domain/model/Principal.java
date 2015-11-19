package br.jus.stf.plataforma.acessos.domain.model;

import java.util.HashSet;
import java.util.Set;

public interface Principal {

	public Set<Permissao> permissoes();
	
	public void atribuirPermissoes(final Set<Permissao> permissoes);
	
	public default boolean possuiAcessoNo(Recurso recurso) {
		Set<Permissao> intersecao = new HashSet<Permissao>(permissoes());
		Set<Permissao> permissoesExigidas = recurso.permissoesExigidas();
		
		intersecao.retainAll(permissoesExigidas);
		
		return permissoesExigidas.equals(intersecao);
	}
	
}
