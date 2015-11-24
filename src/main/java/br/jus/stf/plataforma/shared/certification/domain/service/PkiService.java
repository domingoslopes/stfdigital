package br.jus.stf.plataforma.shared.certification.domain.service;

import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.certification.domain.model.CertificateValidation;
import br.jus.stf.plataforma.shared.certification.domain.model.Pki;
import br.jus.stf.plataforma.shared.certification.domain.model.PkiId;
import br.jus.stf.plataforma.shared.certification.domain.model.PkiRepository;
import br.jus.stf.plataforma.shared.certification.validation.CertificateValidationException;

@Component
public class PkiService {

	private PkiRepository pkiRepository;
	
	public CertificateValidation validate(X509Certificate certificate, PkiId pkiId) {
		Pki pki = pkiRepository.findOne(pkiId);
		if (pki.belongsToPki(certificate)) {
			X509Certificate[] chain = pki.certificateChainOf(certificate);
		}
		try {
			verifyNotExpired(certificate);
			return null;
		} catch (CertificateValidationException e) {
			return null;
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
