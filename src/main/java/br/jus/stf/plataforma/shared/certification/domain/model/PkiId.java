package br.jus.stf.plataforma.shared.certification.domain.model;

import org.apache.commons.lang3.Validate;

public class PkiId {

	private String id;

	public PkiId(String id) {
		Validate.notEmpty(id, "pkiId.id.required");

		this.id = id;
	}

	public String id() {
		return id;
	}

}
