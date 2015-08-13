package br.jus.stf.plataforma.action.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;


/**
 * @author Lucas.Rodrigues
 *
 */
@Embeddable
public class ActionId implements Serializable {

	private static final long serialVersionUID = -5023639053237391210L;
	@Column
	private String id;
	
	ActionId() {
		
	}
	
	/**
	 * @param id
	 */
	public ActionId(String id) {
		Validate.notBlank(id);
		
		this.id = id;
	}

	/**
	 * @return the id
	 */
	@Override
	public String toString() {
		return id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionId other = (ActionId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
