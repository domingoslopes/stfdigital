/**
 * 
 */
package br.jus.stf.plataforma.action.domain.specification;

/**
 * @author Lucas.Rodrigues
 *
 */
public interface Specification<T> {

	boolean isSatisfiedBy(T o);
}
