package br.jus.stf.plataforma.shared.certification.support.pki;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.jus.stf.plataforma.shared.certification.support.CertificationTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CertificationTestConfiguration.class)
public class CustomPKIServiceTest {

	@Autowired
	private CustomPKIService customPKIService;

	@Test
	public void testGerarCertificadoRaiz() throws Exception {
		CustomPKI customPKI = customPKIService.customPKI();
		System.out.println(customPKI);
		Assert.assertNotNull("CustomPKI deveria ter sido construído.", customPKI);
	}

}
