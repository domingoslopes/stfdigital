package br.jus.stf.plataforma.shared.certification.domain.service;

import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.certification.domain.model.certificate.CertificateValidation;
import br.jus.stf.plataforma.shared.certification.domain.model.certificate.CertificateValidationException;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.CompositePki;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.Pki;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.PkiIds;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.PkiRepository;

@Component
public class PkiService {

	@Autowired
	private PkiRepository pkiRepository;
	
	public CertificateValidation validate(X509Certificate certificate, PkiIds pkiIds) {
		try {
			verifyNotExpired(certificate);
			
			Pki[] pkis = pkiRepository.findAll(pkiIds.ids());
			CompositePki pki = new CompositePki(pkis);
			if (pki.belongsToPki(certificate)) {
				X509Certificate[] chain = pki.certificateChainOf(certificate);
				return new CertificateValidation(chain, true);
			} else {
				return new CertificateValidation(new X509Certificate[] { certificate }, false);
			}
		} catch (CertificateValidationException e) {
			return new CertificateValidation(new X509Certificate[] { certificate }, false);
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
