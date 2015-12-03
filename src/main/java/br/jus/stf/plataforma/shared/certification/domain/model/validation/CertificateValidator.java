package br.jus.stf.plataforma.shared.certification.domain.model.validation;

import java.security.cert.X509Certificate;

public interface CertificateValidator {

	CertificateValidation validate(X509Certificate certificate);
	
}
