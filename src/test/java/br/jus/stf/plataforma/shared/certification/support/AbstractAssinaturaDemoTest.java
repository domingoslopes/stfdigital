package br.jus.stf.plataforma.shared.certification.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

import com.itextpdf.text.pdf.PdfReader;

public abstract class AbstractAssinaturaDemoTest {

	protected final String NOME_PDF_DE_TESTE_001 = "pdf-de-teste-001";
	protected final String PDF_DE_TESTE_001 = NOME_PDF_DE_TESTE_001 + ".pdf";

	protected X509Certificate[] recuperarCadeia() throws CertificateException {
		X509Certificate[] cadeia = new X509Certificate[4];
		cadeia[0] = (X509Certificate) AssinaturaTestUtil.getCertificatePessoa001();
		cadeia[1] = (X509Certificate) AssinaturaTestUtil.getCertificateACEmissora();
		cadeia[2] = (X509Certificate) AssinaturaTestUtil.getCertificateACIntermediaria();
		cadeia[3] = (X509Certificate) AssinaturaTestUtil.getCertificateACRaiz();
		return cadeia;
	}

	protected PdfReader getPdfReader() throws Exception {
		return new PdfReader(AssinaturaTestUtil.getInputStreamFromClasspath(PDF_DE_TESTE_001));
	}

	protected byte[] hookAssinarExternamenteSHA1(byte[] sh) {
		System.out.println("Hash para assinatura:" + new String(Base64.encodeBase64(aplicarHashSHA1(sh))));
		String assinaturaBase64 = "";
		return Base64.decodeBase64(assinaturaBase64.getBytes());
	}

	protected byte[] hookAssinarExternamenteSHA256(byte[] sh) {
		System.out.println("Hash para assinatura:" + new String(Base64.encodeBase64(aplicarHashSHA256(sh))));
		String assinaturaBase64 = "";
		return Base64.decodeBase64(assinaturaBase64.getBytes());
	}

	private byte[] aplicarHashSHA1(byte[] arr) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(arr);
			byte hash[] = messageDigest.digest();
			return hash;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Algoritmo de hash não encontrado.", e);
		}
	}

	private byte[] aplicarHashSHA256(byte[] arr) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(arr);
			byte hash[] = messageDigest.digest();
			return hash;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Algoritmo de hash não encontrado.", e);
		}
	}

	protected void checarAssinaturaInternaExterna(byte[] interna, byte[] externa) {
		if (externa.length > 0) {
			if (Arrays.equals(interna, externa)) {
				System.out.println("Assinatura interna e externa coincidem.");
			} else {
				System.err.println("Assinatura interna e externa diferem.");
			}
		} else {
			System.out.println("Não houve assinatura externa, checagem omitida.");
		}
	}

}