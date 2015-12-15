package br.jus.stf.plataforma.shared.security.resource;

import org.apache.commons.lang3.Validate;

import br.jus.stf.plataforma.shared.security.stereotype.Secured;

/**
 * @author Lucas.Rodrigues
 *
 */
public abstract class Resource implements Secured {

	private String id;
	private ResourceType type;
	
	public Resource(String id, ResourceType type) {
		Validate.notBlank(id, "resource.id.required");
		Validate.notNull(type, "resource.type.required");
		
	    this.id = id;
	    this.type = type;
	}
	
	public String id() {
		return id;
	}
	
	public String type() {
		return type.toString();
	}

}
