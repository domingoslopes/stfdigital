package br.jus.stf.plataforma.shared.certification.domain.service;

import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.certification.Pki;
import br.jus.stf.plataforma.shared.certification.validation.CertificateValidationException;

@Component
public class ValidationService {

	public boolean validate(X509Certificate certificate, Pki pki) {
		try {
			verifyNotExpired(certificate);
			return true;
		} catch (CertificateValidationException e) {
			return false;
		}
	}

	private void verifyNotExpired(X509Certificate certificate) throws CertificateValidationException {
		try {
			certificate.checkValidity();
		} catch (CertificateExpiredException e) {
			throw new CertificateValidationException("O certificado se encontra expirado.", e);
		} catch (CertificateNotYetValidException e) {
			throw new CertificateValidationException("O certificado ainda não é válido.", e);
		}
	}
	

}
