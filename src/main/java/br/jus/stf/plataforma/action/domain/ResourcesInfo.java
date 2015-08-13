package br.jus.stf.plataforma.action.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang3.Validate;

/**
 * @author Lucas.Rodrigues
 *
 */
@Embeddable
public class ResourcesInfo implements Serializable {
	
	private static final long serialVersionUID = -4685002737843142465L;

	public enum ResourcesMode { None, One, Many; }
	
	@Column
	private String type = String.class.getSimpleName();
	
	@Enumerated(EnumType.STRING)
	private ResourcesMode mode = ResourcesMode.Many;
	
	ResourcesInfo() {
		
	}
	
	public ResourcesInfo(String type, ResourcesMode mode) {
		Validate.notBlank(type);
		Validate.notNull(mode);
		
		this.type = type;
		this.mode = mode;
	}

	/**
	 * @return the type
	 */
	public String type() {
		return type;
	}
	
	/**
	 * @return the mode, default: Resources.Many
	 */
	public ResourcesMode mode() {
		return mode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mode == null) ? 0 : mode.hashCode());
		result = prime * result
				+ ((type == null) ? 0 : type.hashCode());
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
		ResourcesInfo other = (ResourcesInfo) obj;
		if (mode != other.mode)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
}
