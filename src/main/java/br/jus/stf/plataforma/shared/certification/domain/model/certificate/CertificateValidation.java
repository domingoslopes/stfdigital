package br.jus.stf.plataforma.shared.certification.domain.model.certificate;

import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

public class CertificateValidation {

	private boolean valid;
	private X509Certificate[] certificateChain;
	
	public CertificateValidation(X509Certificate[] chain, boolean valid) {
		this.certificateChain = chain;
		this.valid = valid;
	}

	public boolean valid() {
		return valid;
	}

	public X509Certificate[] certificateChain() {
		return certificateChain;
	}

	public X509CRL[] crls() {
		return new X509CRL[0];
	}

}
