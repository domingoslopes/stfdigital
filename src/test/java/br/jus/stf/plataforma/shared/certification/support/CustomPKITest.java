package br.jus.stf.plataforma.shared.certification.support;

import java.security.cert.X509Certificate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CertificationTestConfiguration.class)
public class CustomPKITest {

	@Autowired
	private CustomPKI customCA;
	
	@Test
	public void testGerarCertificadoRaiz() throws Exception {
		X509Certificate certificate = customCA.generateCertificate();
		System.out.println(certificate);
	}
	
}
