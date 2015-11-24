package br.jus.stf.plataforma.shared.certification.domain.model;

import java.security.cert.X509Certificate;
import java.util.List;

public class Pki {

	private PkiId id;
	
	private List<X509Certificate> rootCertificates;
	
	private List<X509Certificate> intermediateCertificates;
	
	public Pki(PkiId id, List<X509Certificate> rootCerts, List<X509Certificate> intermediateCerts) {
		this.id = id;
		this.rootCertificates = rootCerts;
		this.intermediateCertificates = intermediateCerts;
	}

	public boolean belongsToPki(X509Certificate certificate) {
		return false;
	}

	public X509Certificate[] certificateChainOf(X509Certificate certificate) {
		return null;
	}

}
