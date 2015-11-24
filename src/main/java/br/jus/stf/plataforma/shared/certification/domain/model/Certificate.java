package br.jus.stf.plataforma.shared.certification.domain.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import br.jus.stf.shared.stereotype.Entity;

@javax.persistence.Entity
@Table(name = "CERTIFICADO_DIGITAL", schema = "PLATAFORMA")
public class Certificate implements Entity<Certificate, CertificateId> {

	@EmbeddedId
	private CertificateId id;

	Certificate() {
		
	}
	
	@Override
	public CertificateId id() {
		return id;
	}

	@Override
	public boolean sameIdentityAs(Certificate other) {
		// TODO Auto-generated method stub
		return false;
	}


}
