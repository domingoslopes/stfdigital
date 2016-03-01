package br.jus.stf.plataforma.shared.security.resource;

import java.util.Optional;

import org.apache.commons.lang3.Validate;

import br.jus.stf.plataforma.shared.security.stereotype.Resource;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ResourceImpl implements Resource {

	private String resourceId;
	private ResourceType type;
	
	protected ResourceImpl(ResourceType type) {
		Validate.notNull(type, "resource.type.required");
	    this.type = type;
	}
	
	public ResourceImpl(String resourceId, ResourceType type) {
		this(type);
		Validate.notBlank(resourceId, "resource.resourceId.required");
	    this.resourceId = resourceId;
	}
	
	public String resourceId() {
		return Optional.ofNullable(resourceId).orElseThrow(IllegalStateException::new);
	}
	
	public String type() {
		return type.name();
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((resourceId() == null) ? 0 : resourceId().hashCode());
	    result = prime * result + ((type() == null) ? 0 : type().hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null) return false;
	    if (!(obj instanceof Resource)) return false;
	    Resource other = (Resource) obj;
	    if (resourceId() == null) {
		    if (other.resourceId() != null) return false;
	    } else if (!resourceId().equals(other.resourceId())) return false;
	    if (!type().equals(other.type())) return false;
	    return true;
    }
	
}
