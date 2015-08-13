package br.jus.stf.plataforma.action.domain;

import java.util.Collection;
import java.util.Set;

import br.jus.stf.plataforma.action.domain.exception.ActionUnavailableException;

/**
 * Pesquisa e executa ações que estão definidas em algum módulo
 * 
 * @author Lucas.Rodrigues
 *
 */
public interface ActionAware {

	/**
	 * Pesquisa ações localmente e valida no módulo de origem aquelas que possuem outras restrições
	 * retornando as que o usuário possui acesso 
	 * 
	 * @param spec
	 * @return as ações permitidas
	 */
	public Set<Action> search(SearchSpecification spec) throws Exception;
	
	/**
	 * Executa uma ação no módulo de origem
	 * 
	 * @param action
	 * @param resources
	 * @throws ActionUnavailableException
	 */
	public void execute(Action action, Collection<?> resources) throws ActionUnavailableException;	
}
