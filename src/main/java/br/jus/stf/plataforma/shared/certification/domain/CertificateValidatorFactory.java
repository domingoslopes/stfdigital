package br.jus.stf.plataforma.shared.certification.domain;

import br.jus.stf.plataforma.shared.certification.domain.model.pki.Pki;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.CertificateValidator;

public interface CertificateValidatorFactory {

	CertificateValidator createCertificateValidator(Pki pki);

	CertificateValidator createCertificateValidator(Pki[] pkis);

}
