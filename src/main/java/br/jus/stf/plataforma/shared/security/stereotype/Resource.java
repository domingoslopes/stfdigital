package br.jus.stf.plataforma.shared.security.stereotype;

import java.lang.reflect.ParameterizedType;

import org.apache.commons.lang3.Validate;

/**
 * @author Lucas.Rodrigues
 *
 */
public abstract class Resource<T> implements Secured {

	private Class<T> clazz;
	private String id;
	
	public Resource(String id) {
		Validate.notBlank(id, "resource.id.required");
		
	    this.clazz = getClazz();
	    this.id = id;
	}
	
	public String id() {
		return id;
	}
	
	@Override
	public String type() {
		return clazz.getSimpleName().toUpperCase();
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getClazz() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
	    return (Class<T>) type.getActualTypeArguments()[0];
	}

}
