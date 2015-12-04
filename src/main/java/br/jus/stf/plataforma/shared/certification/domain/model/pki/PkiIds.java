package br.jus.stf.plataforma.shared.certification.domain.model.pki;

public class PkiIds {
	
	private PkiId[] pkiIds;
	
	public PkiIds(PkiId... pkiIds) {
		this.pkiIds = pkiIds;
	}

	public PkiId[] ids() {
		return pkiIds;
	}

}
