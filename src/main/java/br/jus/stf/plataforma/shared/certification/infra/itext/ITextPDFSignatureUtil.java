package br.jus.stf.plataforma.shared.certification.infra.itext;

import java.security.cert.X509CRL;
import java.util.Collection;

public class ITextPDFSignatureUtil {

	private ITextPDFSignatureUtil() {

	}

	public static int estimateSignatureSize(X509CRL[] crls) {
		return 0;
	}

	public static Collection<byte[]> crlsToByteCollection(X509CRL[] crls) {
		// TODO Auto-generated method stub
		return null;
	}

	public static byte[] applyHash(byte[] authAttrs, String hashAlgorithmName) {
		// TODO Auto-generated method stub
		return null;
	}

}
