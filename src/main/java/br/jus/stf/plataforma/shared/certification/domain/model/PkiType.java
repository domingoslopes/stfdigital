package br.jus.stf.plataforma.shared.certification.domain.model;

public enum PkiType {

	ICP_BRASIL("ICP Brasil"), ICP_PLATAFORMA("ICP Plataforma");

	private String description;

	private PkiType(String desc) {
		this.description = desc;
	}

	public PkiId id() {
		return new PkiId(name());
	}

	public String description() {
		return description;
	}

}
