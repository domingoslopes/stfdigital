package br.jus.stf.plataforma.shared.certification.domain.model;

import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

public class PDFSigningSpecification implements SigningSpecification {

	@Override
	public DocumentType documentType() {
		return DocumentType.PDF;
	}

	@Override
	public SigningStrategy strategy() {
		// TODO Auto-generated method stub
		return null;
	}

	public String reason() {
		// TODO Auto-generated method stub
		return null;
	}

	public X509CRL[] crls() {
		return null;
	}

	public String hashAlgorithmName() {
		return "SHA-256";
	}

	public X509Certificate[] certificateChain() {
		// TODO Auto-generated method stub
		return null;
	}

}
