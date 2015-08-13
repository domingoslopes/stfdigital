package br.jus.stf.plataforma.action.infra;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.jus.stf.plataforma.action.domain.Action;
import br.jus.stf.plataforma.action.domain.ResourcesInfo;
import br.jus.stf.plataforma.action.domain.ResourcesInfo.ResourcesMode;
import br.jus.stf.plataforma.action.domain.SearchSpecification;

import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Implementação da especificação de critérios para a pesquisa de ações
 * 
 * @author Lucas.Rodrigues
 *
 */
public class SearchSpecificationImpl implements SearchSpecification {
	
	private String context;
	private String resourcesType;
	private ArrayNode resources;
	
	public SearchSpecificationImpl(String context, String resourcesType, ArrayNode resources) {
		this.context = context;
		this.resourcesType = resourcesType;
		this.resources = resources;
	}
	
	@Override
	public String context() {
		return context;
	}
	
	@Override
	public String resourcesType() {
		return resourcesType;
	}
	
	@Override
	public ArrayNode resources() {
		return resources;
	}
	
	@Override
	public Predicate toPredicate(Root<Action> root, CriteriaQuery<?> query,	CriteriaBuilder cb) {
		Predicate type = getResourcesTypeRestriction(root, cb);
		Predicate mode = getResourcesModeRestriction(root, cb);
		
		if (!StringUtils.isEmpty(context)) {
			Predicate context = getContextRestriction(root, cb);
			return cb.and(context, type, mode);
		}
		return cb.and(type, mode);
	}

	/**
	 * @param root
	 * @param cb
	 * @return a restrição de contexto
	 */
	private Predicate getContextRestriction(Root<Action> root,
			CriteriaBuilder cb) {
		Path<String> pathContext = root.<String>get("context");
		Predicate equalContext = cb.equal(pathContext, context);
		return equalContext;
	}

	/**
	 * @param root
	 * @param cb
	 * @return a restrição do modo de recursos
	 */
	private Predicate getResourcesModeRestriction(Root<Action> root, CriteriaBuilder cb) {
		Path<ResourcesMode> pathMode = root.<ResourcesInfo>get("resourcesInfo").get("mode");
		Collection<ResourcesMode> modes = ResourcesInfo.possibleModeFrom(resources);
		return cb.and(pathMode.in(modes));
	}

	/**
	 * @param root
	 * @param cb
	 * @return a restrição do tipo de recurso
	 */
	private Predicate getResourcesTypeRestriction(Root<Action> root, CriteriaBuilder cb) {
		Path<String> pathType = root.<ResourcesInfo>get("resourcesInfo").get("type");
		return cb.equal(pathType, resourcesType);
	}

}
