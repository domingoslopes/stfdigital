package br.jus.stf.plataforma.shared.certification.pki;

import br.jus.stf.plataforma.shared.certification.Pki;

public class RepositoryBasedPki implements Pki {

	private String id;

	public RepositoryBasedPki(String id) {
		this.id = id;
	}

	public String id() {
		return id;
	}

}
