package br.jus.stf.plataforma.shared.certification.infra;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.certification.domain.CertificateValidatorFactory;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.CompositePki;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.Pki;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.CertificateValidator;
import br.jus.stf.plataforma.shared.certification.infra.pki.BouncyCastlePkiCertificateValidator;

@Component
public class CertificateValidatorFactoryImpl implements CertificateValidatorFactory {

	@Override
	public CertificateValidator createCertificateValidator(Pki pki) {
		return new BouncyCastlePkiCertificateValidator(pki);
	}

	@Override
	public CertificateValidator createCertificateValidator(Pki[] pkis) {
		return createCertificateValidator(new CompositePki(pkis));
	}

}
