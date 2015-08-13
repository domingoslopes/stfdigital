/**
 * 
 */
package br.jus.stf.plataforma.action.domain.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import br.jus.stf.plataforma.action.domain.Action;

import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * @author Lucas.Rodrigues
 *
 */
public class SearchSpecification implements Specification<Action> {
	
	private String context;
	private String resourcesType;
	private ArrayNode resources;
	
	public SearchSpecification(String context, String resourcesType, ArrayNode resources) {
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
	public Predicate toPredicate(Root<Action> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		
		Predicate equalType = cb.equal(root.<String>get("resourcesInfo.type"), resourcesType);
		Predicate equalMode = null;
		
		if (resources.size() == 0) {
			//equalMode = cb.or(x, y)
		} else {
			
		}
		
		
		
		if (!StringUtils.isEmpty(context)) {
			Predicate equalContext = cb.equal(root.<String>get("context"), context);
			return cb.and(equalContext, equalType);
		} else {
			return equalType;
		}
	}

}
