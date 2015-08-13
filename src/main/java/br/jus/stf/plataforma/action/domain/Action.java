package br.jus.stf.plataforma.action.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeanUtils;



/**
 * @author Lucas.Rodrigues
 *
 */
@Entity
@Table
public class Action implements Serializable {

	private static final long serialVersionUID = -6171552302897908287L;
	
	@EmbeddedId
	private ActionId id;
	
	@Column
	private String description;
	
	@Column
	private String context;
	
	@Column
	private Boolean hasConditionHandlers = false;
	
	@Embedded
	private ResourcesInfo resourcesInfo = new ResourcesInfo();
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn
	private Set<ActionAuthority> grantedAuthorities = new HashSet<ActionAuthority>(0);
	
	Action() {
		
	}
	
	/**
	 * @param id
	 * @param description
	 */
	public Action(ActionId id, String description, String context) {
		Validate.notNull(id);
		Validate.notBlank(description);
		Validate.notBlank(context);
		
		this.id = id;
		this.description = description;
		this.context = context;
	}
	
	/**
	 * @return the id
	 */
	public ActionId id() {
		return id;
	}
	
	/**
	 * @return the description
	 */
	public String description() {
		return description;
	}
	
	/**
	 * @return the context
	 */
	public String context() {
		return context;
	}
	
	/**
	 * @return the hasConditionHandlers
	 */
	public boolean hasConditionHandlers() {
		return hasConditionHandlers;
	}
	
	/**
	 * @return the resourcesInfo
	 */
	public ResourcesInfo resourcesInfo() {
		return resourcesInfo;
	}
	
	/**
	 * @return the grantedAuthorities
	 */
	public Set<ActionAuthority> grantedAuthorities() {
		return Collections.unmodifiableSet(grantedAuthorities);
	}
	
	/**
	 * Se true faz com que as ações sejam verificadas no componente de ações
	 * usado por um módulo
	 * 
	 * @param hasConditionHandlers
	 */
	public void hasConditionHandlers(boolean hasConditionHandlers) {
		this.hasConditionHandlers = hasConditionHandlers;
	}
	
	/**
	 * Altera as metainformações dos recursos alvos da ação
	 * 
	 * @param resourcesInfo
	 */
	public void changeResourcesInfo(ResourcesInfo resourcesInfo) {
		Validate.notNull(resourcesInfo);
		this.resourcesInfo = resourcesInfo;
	}
	
	/**
	 * Permite acesso a um papel
	 * 
	 * @param authority
	 */
	public void grantAccessTo(ActionAuthority authority) {
		Validate.notNull(authority);
		grantedAuthorities.add(authority);
	}
	
	/**
	 * Retira o acesso a um papel
	 * 
	 * @param authority
	 */
	public void denyAccessTo(ActionAuthority authority) {
		Validate.notNull(authority);
		grantedAuthorities.remove(authority);
	}
	
	/**
	 * Varifica se possui os papéis de acesso
	 * 
	 * @param authority
	 * @return
	 */
	public boolean hasGrantedAccess(Set<ActionAuthority> authorities) {
		if (grantedAuthorities.size() == 0) {
			return true;
		}
		return authorities.containsAll(grantedAuthorities);
	}
	
	/**
	 * Atualiza uma ação a partir de outra
	 * 
	 * @param source
	 */
	public void updateFrom(Action source) {
		BeanUtils.copyProperties(source, this, "id", "grantedAuthorities");
		this.grantedAuthorities.retainAll(source.grantedAuthorities);
	}
	
	/**
	 * Atualiza uma ação a partir de outra que está numa coleção
	 * e remove da coleção
	 * 
	 * @param sources
	 */
	public void updateFrom(Collection<Action> sources) {
		sources.stream()
			.filter(s -> this.equals(s))
			.forEach(s -> this.updateFrom(s));
		sources.remove(this);		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
