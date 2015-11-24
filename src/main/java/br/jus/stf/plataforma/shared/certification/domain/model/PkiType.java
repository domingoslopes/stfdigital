package br.jus.stf.plataforma.shared.certification.domain.model;

import br.jus.stf.plataforma.shared.certification.pki.RepositoryBasedPki;

public enum PkiType {

	ICP_BRASIL("icp-brasil"), ICP_PLATAFORMA("icp-plataforma");

	private String id;
	private Pki pki;

	private PkiType(String id) {
		this.id = id;
		this.pki = createPki();
	}

	private Pki createPki() {
		return new RepositoryBasedPki(id);
	}

	public Pki instance() {
		return pki;
	}

	public String id() {
		return id;
	}

}
