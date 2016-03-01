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
	public Set<Recurso> recursos();
	
	/**
	 * @param recursos
	 */
	public void atribuirRecursos(final Set<Recurso> recursos);
	
	/**
	 * @param recursos
	 */
	public void removerRecursos(final Set<Recurso> recursos);
	
	/**
	 * @param recurso
	 * @return
	 */
	public default boolean possuiAcessoNo(Recurso recurso) {
		return recursos().contains(recurso);
	}
	
}
