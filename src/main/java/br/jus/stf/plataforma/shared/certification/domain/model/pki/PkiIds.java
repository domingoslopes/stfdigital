package br.jus.stf.plataforma.shared.certification.domain.model.pki;

public class PkiIds {
	
	private PkiId[] ids;
	
	public PkiIds(PkiId... ids) {
		this.ids = ids;
	}

	public PkiId[] ids() {
		return this.ids;
	}

}
