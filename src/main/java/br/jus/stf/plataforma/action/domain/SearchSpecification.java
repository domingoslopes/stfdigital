package br.jus.stf.plataforma.action.domain;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

/**
 * Abstração da especificação de critérios para a pesquisa de ações
 * 
 * @author Lucas.Rodrigues
 *
 */
public interface SearchSpecification extends Specification<Action> {

	/**
	 * @return the context
	 */
	public abstract String context();

	/**
	 * @return the resourcesType
	 */
	public abstract String resourcesType();

	/**
	 * @return the resources
	 */
	public abstract Collection<?> resources();

}