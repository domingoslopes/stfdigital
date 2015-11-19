package br.jus.stf.plataforma.shared.certification.signature;

import org.apache.commons.lang3.Validate;

public class SignatureContextId {

	private final String id;
	
	public SignatureContextId(String id) {
		Validate.notEmpty(id);
		
		this.id = id;
	}
	
	public String id() {
		return id;
	}
	
}
