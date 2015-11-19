package br.jus.stf.plataforma.shared.certification.support.pki;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.certification.AbstractCertificationTest;

public class CustomPkiServiceTest extends AbstractCertificationTest {

	@Autowired
	private CustomPkiService customPKIService;

	@Test
	public void testGerarCertificadoRaiz() throws Exception {
		CustomPki customPKI = customPKIService.customPKI();
		System.out.println(customPKI);
		Assert.assertNotNull("CustomPKI deveria ter sido construído.", customPKI);
	}

}
