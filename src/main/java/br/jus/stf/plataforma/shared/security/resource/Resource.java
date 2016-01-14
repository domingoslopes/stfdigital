package br.jus.stf.plataforma.shared.security.resource;

import java.util.Optional;

import org.apache.commons.lang3.Validate;

import br.jus.stf.plataforma.shared.security.stereotype.Secured;

/**
 * @author Lucas.Rodrigues
 *
 */
public abstract class Resource implements Secured {

	private String resourceId;
	private ResourceType type;
	
	protected Resource(ResourceType type) {
		Validate.notNull(type, "resource.type.required");
	    this.type = type;
	}
	
	public Resource(String resourceId, ResourceType type) {
		this(type);
		Validate.notBlank(resourceId, "resource.resourceId.required");
	    this.resourceId = resourceId;
	}
	
	public String resourceId() {
		return Optional.ofNullable(resourceId).orElseThrow(IllegalStateException::new);
	}
	
	public String type() {
		return type.toString();
	}

}
