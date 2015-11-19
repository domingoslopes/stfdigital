package br.jus.stf.plataforma.shared.certification.support;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PdfPKCS7;

import br.jus.stf.plataforma.shared.certification.signature.SignatureContext;

public class SHA256DetachedAssinadorPorPartes extends AssinadorPorPartes {

	public SHA256DetachedAssinadorPorPartes(boolean hasTSA) {
		super(hasTSA);
	}

	@Override
	protected byte[] preGerarHashes(Certificate[] cadeia, CRL[] crls, SignatureContext ca, PdfStamper stamper, PdfSignatureAppearance appearance)
			throws AssinaturaExternaException {
		try {
			int tamanhoEstimado = calcularTamanhoEstimado(crls);

			ExternalDigest externalDigest = new ExternalDigest() {
				@Override
				public MessageDigest getMessageDigest(String hashAlgorithm) throws GeneralSecurityException {
					return DigestAlgorithms.getMessageDigest("SHA-256", null);
				}
			};
			
			PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
			dic.setReason(appearance.getReason());
			dic.setDate(new PdfDate(appearance.getSignDate()));
			appearance.setCryptoDictionary(dic);
			HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
			exc.put(PdfName.CONTENTS, new Integer(tamanhoEstimado * 2 + 2));
			appearance.preClose(exc);

//			PdfPKCS7 sgnNew = new PdfPKCS7(null, cadeia, crls, "SHA256", null, false);
			PdfPKCS7 sgnNew = new PdfPKCS7(null, cadeia, "SHA256", null, externalDigest, false);
//			sgnNew.getCRLs().addAll(Arrays.asList(crls));

			InputStream data = appearance.getRangeStream();
			byte primeiroHash[] = DigestAlgorithms.digest(data, externalDigest.getMessageDigest("SHA256"));
			
			List<byte[]> crlsL = new ArrayList<>();
			for (CRL crl : crls) {
				X509CRL crlX = (X509CRL)crl;
				crlsL.add(crlX.getEncoded());
			}
			
			Calendar cal = Calendar.getInstance();
			sgnNew.setSignDate(cal);
			byte[] authAttrs = sgnNew.getAuthenticatedAttributeBytes(primeiroHash, null, crlsL, CryptoStandard.CMS);
			
//			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//			byte buf[] = new byte[8192];
//			int n;
//			InputStream inp = appearance.getRangeStream();
//			while ((n = inp.read(buf)) > 0) {
//				messageDigest.update(buf, 0, n);
//			}
//			byte primeiroHash[] = messageDigest.digest();
//			Calendar cal = Calendar.getInstance();
//			byte[] authAttrs = sgnNew.getAuthenticatedAttributeBytes(primeiroHash, cal, null);

			ca.setAppearance(appearance);
			ca.setPrimeiroHash(primeiroHash);
			ca.setPdfPKCS7(sgnNew);
			ca.setSignDate(cal);
			ca.setTamanhoEstimado(tamanhoEstimado);
			ca.setCrls(crlsL);

			return authAttrs;
		} catch (IOException e) {
			throw new AssinaturaExternaException("Erro ler pré-assinatura do PDF..", e);
		} catch (DocumentException e) {
			throw new AssinaturaExternaException("Erro ao inciar assinatura do PDF.", e);
		} catch (NoSuchAlgorithmException e) {
			throw new AssinaturaExternaException("Erro ao calcular hash do PDF.", e);
		} catch (InvalidKeyException e) {
			throw new AssinaturaExternaException("Erro gerar assinatura PKCS7. Chave não encontrada.", e);
		} catch (NoSuchProviderException e) {
			throw new AssinaturaExternaException("Erro gerar assinatura PKCS7. Provedor não encontrado.", e);
		} catch (CRLException e) {
			throw new AssinaturaExternaException("Erro ao estimar tamanho da assinatura.", e);
		} catch (GeneralSecurityException e) {
			throw new AssinaturaExternaException("Erro genérico de segurança.", e);
		}
	}

	private byte[] aplicarHashSHA256(byte[] arr) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(arr);
			byte hash[] = messageDigest.digest();
			return hash;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Algoritmo de hash SHA-256 não encontrado.", e);
		}
	}

	@Override
	protected void posAssinarImpl(SignatureContext ca, PdfSignatureAppearance appearance, byte[] primeiroHash, HashSignature assinatura)
			throws AssinaturaExternaException {
		try {
			int tamanhoEstimado = ca.getTamanhoEstimado();
			PdfPKCS7 sgnNew = ca.getPdfPKCS7();
			sgnNew.setExternalDigest(assinatura.signatureAsBytes(), null, "RSA");

//			TSAClient tsaClient = getTSAClient();
			Collection<byte[]> crls = ca.getCrls();
			
			byte[] encodedSig = sgnNew.getEncodedPKCS7(primeiroHash, null, null, crls, CryptoStandard.CMS);
			byte[] paddedSig = new byte[tamanhoEstimado];
			System.arraycopy(encodedSig, 0, paddedSig, 0, encodedSig.length);

			PdfDictionary dic2 = new PdfDictionary();
			dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));

			appearance.close(dic2);
		} catch (IOException e) {
			throw new AssinaturaExternaException("Erro ao finalizar montagem do PDF.", e);
		} catch (DocumentException e) {
			throw new AssinaturaExternaException("Erro ao fechar PDF assinado.", e);
		}
	}

	@Override
	public byte[] prepararHashParaAssinaturaExterna(byte[] dataToSign) {
		return aplicarHashSHA256(dataToSign);
	}

}
