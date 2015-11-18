package br.jus.stf.plataforma.shared.certification.support.pki;

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
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Component;

@Component
public class CustomPKIService {

	@PostConstruct
	public void init() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		customPKI = generateCustomPKI();
	}

	private CustomPKI customPKI;
	
	public CustomPKI customPKI() {
		return customPKI;
	}
	
	private String randomUnit = "";
	
	private CustomPKI generateCustomPKI() throws Exception {
		randomUnit = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
		CustomPKIStore rootCA = generateRootCA();
		CustomPKIStore intermediateCA = generateIntermediateCA(rootCA);
		CustomPKIStore finalUser = generateFinalUser(intermediateCA);
		return new CustomPKI(rootCA, intermediateCA, finalUser);
	}

	private CustomPKIStore generateRootCA() throws Exception {
		KeyPair kp = generateKeyPair(4096);

		PublicKey publicKey = kp.getPublic();
		PrivateKey privateKey = kp.getPrivate();

		String random = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

		String issuer = "CN = ROOT CA CUSTOM " + random + " v1 OU = UNIT " + randomUnit + " O = STF C = BR";
		String subject = issuer; // Auto assinado.
		BigInteger serial = BigInteger.valueOf(1L);
		Date notBefore = new Date(System.currentTimeMillis());
		Date notAfter = new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 365 * 23));

		X509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(new X500Name(issuer), serial, notBefore,
				notAfter, new X500Name(subject), publicKey);

		X509CertificateHolder holder = builder
				.build(new JcaContentSignerBuilder("SHA512WithRSA").setProvider("BC").build(privateKey));

		X509Certificate certificate = new JcaX509CertificateConverter().setProvider("BC").getCertificate(holder);

		return new CustomPKIStore(kp, certificate);
	}

	private CustomPKIStore generateIntermediateCA(CustomPKIStore ca) throws Exception {
		KeyPair kp = generateKeyPair(4096);

		PublicKey publicKey = kp.getPublic();

		String random = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
		String subject = "CN = INT CA CUSTOM " + random + " v1 OU = UNIT " + randomUnit + " O = STF C = BR";

		BigInteger serial = BigInteger.valueOf(1L);
		Date notBefore = new Date(System.currentTimeMillis());
		Date notAfter = new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 365 * 5));

		X509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(ca.certificate(), serial, notBefore,
				notAfter, new X500Name(subject), publicKey);

		JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();

		builder.addExtension(Extension.subjectKeyIdentifier, false, extUtils.createSubjectKeyIdentifier(publicKey));
		builder.addExtension(Extension.authorityKeyIdentifier, false,
				extUtils.createAuthorityKeyIdentifier(ca.certificate()));
		builder.addExtension(Extension.basicConstraints, true, new BasicConstraints(0));

		X509CertificateHolder holder = builder
				.build(new JcaContentSignerBuilder("SHA512WithRSA").setProvider("BC").build(ca.keyPair().getPrivate()));

		X509Certificate certificate = new JcaX509CertificateConverter().setProvider("BC").getCertificate(holder);

		return new CustomPKIStore(kp, certificate);
	}
	
	private CustomPKIStore generateFinalUser(CustomPKIStore ca) throws Exception {
		KeyPair kp = generateKeyPair(2048);

		PublicKey publicKey = kp.getPublic();

		String random = RandomStringUtils.randomAlphabetic(5).toUpperCase();
		String subject = "CN = FULANO DA SILVA " + random + " v1 OU = UNIT " + randomUnit + " O = STF C = BR";

		BigInteger serial = BigInteger.valueOf(1L);
		Date notBefore = new Date(System.currentTimeMillis());
		Date notAfter = new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 365 * 1));
		
		X509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(ca.certificate(), serial, notBefore,
				notAfter, new X500Name(subject), publicKey);
		
		JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();

		builder.addExtension(Extension.subjectKeyIdentifier, false, extUtils.createSubjectKeyIdentifier(publicKey));
		builder.addExtension(Extension.authorityKeyIdentifier, false,
				extUtils.createAuthorityKeyIdentifier(ca.certificate()));
		
		X509CertificateHolder holder = builder
				.build(new JcaContentSignerBuilder("SHA256WithRSA").setProvider("BC").build(ca.keyPair().getPrivate()));

		X509Certificate certificate = new JcaX509CertificateConverter().setProvider("BC").getCertificate(holder);

		return new CustomPKIStore(kp, certificate);
	}
	
	private KeyPair generateKeyPair(int keySize) throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(keySize);
		
		KeyPair kp = kpg.generateKeyPair();
		return kp;
	}

}
