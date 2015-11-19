package br.jus.stf.plataforma.shared.certification;

import br.jus.stf.plataforma.shared.certification.pki.IcpBrasilPki;

public enum PkiType {

	ICP_BRASIL("icp-brasil") {
		@Override
		protected Pki createPki() {
			return new IcpBrasilPki();
		}
	};

	private String id;
	private Pki pki;

	private PkiType(String id) {
		this.id = id;
		this.pki = createPki();
	}

	protected abstract Pki createPki();

	public Pki instance() {
		return pki;
	}

	public String id() {
		return id;
	}

}
