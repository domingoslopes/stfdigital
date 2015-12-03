package br.jus.stf.plataforma.shared.certification.infra.pki;

import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

import br.jus.stf.plataforma.shared.certification.domain.model.pki.Pki;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.CertificateValidation;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.CertificateValidationException;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.CertificateValidator;

public class BouncyCastlePkiCertificateValidator implements CertificateValidator {

	private Pki pki;

	public BouncyCastlePkiCertificateValidator(Pki pki) {
		this.pki = pki;
	}

	@Override
	public CertificateValidation validate(X509Certificate certificate) {
		CertificateValidation validation = new CertificateValidation();
		try {
			verifyNotExpired(certificate);
			if (!pki.belongsToPki(certificate)) {
				validation.associateChain(new X509Certificate[] { certificate });
				throw new CertificateValidationException(
						"Certificado não pertence a nehuma infraestrutura de chaves públicas confiável.");
			}
			X509Certificate[] chain = pki.certificateChainOf(certificate);
			validation.associateChain(chain);

		} catch (CertificateValidationException e) {
			validation.appendValidationError(e.getMessage());
		}
		return validation;
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
