package br.jus.stf.plataforma.acessos.domain.model;

import java.util.Set;

/**
 * @author Rafael Esdras
 *
 */
public interface Principal {

	/**
	 * @return
	 */
	public Set<Permissao> permissoes();
	
	/**
	 * @param permissoes
	 */
	public void atribuirPermissoes(final Set<Permissao> permissoes);
	
	/**
	 * @param permissoes
	 */
	public void removerPermissoes(final Set<Permissao> permissoes);
	
	/**
	 * @param recurso
	 * @return
	 */
	public default boolean possuiAcessoNo(Recurso recurso) {
		if (recurso.permissoesExigidas().isEmpty()) {
			return true;
		}
		return permissoes().containsAll(recurso.permissoesExigidas());
	}
	
}
