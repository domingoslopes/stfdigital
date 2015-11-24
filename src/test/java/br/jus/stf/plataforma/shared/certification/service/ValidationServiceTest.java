package br.jus.stf.plataforma.shared.certification.service;

import java.security.cert.X509Certificate;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.certification.AbstractCertificationTest;
import br.jus.stf.plataforma.shared.certification.domain.model.Pki;
import br.jus.stf.plataforma.shared.certification.domain.service.CertificateValidationService;
import br.jus.stf.plataforma.shared.certification.support.pki.CustomPkiService;

public class ValidationServiceTest extends AbstractCertificationTest {

	@Autowired
	private CustomPkiService customPkiService;

	@Autowired
	private CertificateValidationService validationService;

	@Test
	public void testValidate() {
		X509Certificate certificate = customPkiService.customPKI().finalUser().certificate();
		boolean valid = validationService.validate(certificate, new Pki() {});
		Assert.assertTrue("Deveria ser válido.", valid);
	}

}
