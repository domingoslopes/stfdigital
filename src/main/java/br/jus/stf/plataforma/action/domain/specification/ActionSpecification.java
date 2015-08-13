package br.jus.stf.plataforma.action.domain.specification;

import br.jus.stf.plataforma.action.domain.Action;

import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * @author Lucas.Rodrigues
 *
 */
//TODO Lucas.Rodrigues: Alterar para jpaspecification do spring data
public class ActionSpecification implements Specification<Action> {

	private String context;
	private String resourcesType;
	private ArrayNode resources;
	
	public ActionSpecification(String context, String resourcesType, ArrayNode resources) {
		this.context = context;
		this.resourcesType = resourcesType;
		this.resources = resources;
	}
	
	public String context() {
		return context;
	}
	
	public String resourcesType() {
		return resourcesType;
	}
	
	public ArrayNode resources() {
		return resources;
	}
	
	@Override
	public boolean isSatisfiedBy(Action o) {
		if (context.equals(o.context())) {
			return true;
		}
		return false;
	}
	
}
