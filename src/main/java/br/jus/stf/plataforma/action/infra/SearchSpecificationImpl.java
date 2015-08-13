package br.jus.stf.plataforma.action.infra;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.jus.stf.plataforma.action.domain.Action;
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
		Path<ResourcesMode> pathMode = root.<ResourcesMode>get("resourcesInfo.mode");
		
		if (resources.size() == 0) {
			return cb.equal(pathMode, ResourcesMode.None);
		} else if (resources.size() > 1){
			return cb.equal(pathMode, ResourcesMode.Many);
		}
		Predicate equalModeOne = cb.equal(pathMode, ResourcesMode.One);
		Predicate equalModeMany = cb.equal(pathMode, ResourcesMode.Many);
		return cb.or(equalModeOne, equalModeMany);
	}

	/**
	 * @param root
	 * @param cb
	 * @return a restrição do tipo de recurso
	 */
	private Predicate getResourcesTypeRestriction(Root<Action> root, CriteriaBuilder cb) {
		Path<String> pathType = root.<String>get("resourcesInfo.type");
		return cb.equal(pathType, resourcesType);
	}

}
