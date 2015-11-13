package br.jus.stf.plataforma.shared.certification.support;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Component;

@Component
public class CustomPKI {
	
	@PostConstruct
	public void init() {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public X509Certificate generateCertificate() throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(4096);
		
		KeyPair kp = kpg.generateKeyPair();
		
		PublicKey publicKey = kp.getPublic();
		PrivateKey privateKey = kp.getPrivate();
		
		String random = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
		
		String issuer = "CN = AC RAIZ CUSTOM " + random + " v1 OU = UNIT " + random + " O = STF C = BR";
		String subject = issuer; // Auto assinado.
		BigInteger serial = BigInteger.valueOf(1L);
		Date notBefore = new Date(System.currentTimeMillis());
		Date notAfter = new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 365 * 23));
		
		X509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(new X500Name(issuer), serial, notBefore, notAfter, new X500Name(subject), publicKey);
		
		X509CertificateHolder holder = builder.build(new JcaContentSignerBuilder("SHA512WithRSA").setProvider("BC").build(privateKey));
		
		X509Certificate certificate = new JcaX509CertificateConverter().setProvider("BC").getCertificate(holder);
		
		return certificate;
		
//		X509v3CertificateBuilder builder = new X509v3CertificateBuilder(issuer, serial, notBefore, notAfter, subject, publicKeyInfo)
	}
	
}
