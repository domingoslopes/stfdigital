package br.jus.stf.plataforma.action.domain;

import java.util.ArrayList;
import java.util.List;

import br.jus.stf.plataforma.action.domain.ResourcesInfo.ResourcesMode;

/**
 * Builder de ações que disponibiliza um builder para preencher as informações
 * 
 * @author Lucas.Rodrigues
 *
 */
public class ActionBuilder {
	
	private String id;
	private String description;
	private String context;
	private ResourcesInfo resourcesInfo = new ResourcesInfo();
	private boolean hasConditionHandlers = false;
	private List<ActionAuthority> authorities = new ArrayList<ActionAuthority>(0);
	
	/**
	 * Cria o builder com as informações básicas
	 * 
	 * @param id
	 * @param description
	 * @param context
	 */
	public ActionBuilder(String id, String description, String context) {
		this.id = id;
		this.description = description;
		this.context = context;
	}
	
	/**
	 * Define as informações dos recursos
	 * 
	 * @param resourcesType
	 * @param resourcesMode
	 * @return o builder
	 */
	public ActionBuilder withResourcesInfo(String resourcesType, String resourcesMode) {
		ResourcesMode mode = ResourcesMode.valueOf(resourcesMode);
		resourcesInfo = new ResourcesInfo(resourcesType, mode);
		return this;
	}
	
	/**
	 * Popula os pápeis que permitirão acesso à ação
	 * 
	 * @param grantedAuthorities
	 * @return o builder
	 */
	public ActionBuilder withAuthorithies(List<String> grantedAuthorities) {
		grantedAuthorities
			.forEach(authority -> authorities.add(new ActionAuthority(authority)));
		return this;
	}
	
	/**
	 * Marca se a ação tem handlers no módulo
	 * 
	 * @param hasConditionHandlers
	 * @return o builder
	 */
	public ActionBuilder withConditionHandlers(Boolean hasConditionHandlers) {
		this.hasConditionHandlers = Boolean.TRUE.equals(hasConditionHandlers);
		return this;
	}
	
	/**
	 * Retorna a ação criada
	 * 
	 * @return a ação
	 */
	public Action build() {
		ActionId actionId = new ActionId(id);
		Action action = new Action(actionId, description, context);
		action.changeResourcesInfo(resourcesInfo);
		action.hasConditionHandlers(hasConditionHandlers);
		authorities.forEach(authority -> action.grantAccessTo(authority));
		return action;
	}
	
}
