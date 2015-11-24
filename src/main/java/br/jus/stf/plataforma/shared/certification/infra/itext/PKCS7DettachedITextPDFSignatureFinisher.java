package br.jus.stf.plataforma.shared.certification.infra.itext;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CRLException;
import java.util.Calendar;
import java.util.HashMap;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PdfPKCS7;

import br.jus.stf.plataforma.shared.certification.domain.model.HashToSign;
import br.jus.stf.plataforma.shared.certification.domain.model.HashType;
import br.jus.stf.plataforma.shared.certification.domain.model.PDFSigningSpecification;
import br.jus.stf.plataforma.shared.certification.domain.model.PreSignature;
import br.jus.stf.plataforma.shared.certification.support.AuthenticatedAttributes;
import br.jus.stf.plataforma.shared.certification.support.SignatureException;

public class PKCS7DettachedITextPDFSignatureFinisher implements ITextPDFSignatureFinisher {

	private PdfSignatureAppearance appearance;
	private byte[] firstHash;
	private PdfPKCS7 pdfPKCS7;
	private Calendar signDate;
	private int estimatedSize;

	@Override
	public PreSignature finishPreSignature(final PDFSigningSpecification spec, PdfSignatureAppearance appearance) throws SignatureException {
		try {
			int estimatedSize = ITextPDFSignatureUtil.estimateSignatureSize(spec.crls());

			ExternalDigest externalDigest = new ExternalDigest() {
				@Override
				public MessageDigest getMessageDigest(String hashAlgorithm) throws GeneralSecurityException {
					return DigestAlgorithms.getMessageDigest(hashAlgorithm, null);
				}
			};

			PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
			dic.setReason(appearance.getReason());
			dic.setDate(new PdfDate(appearance.getSignDate()));
			appearance.setCryptoDictionary(dic);
			HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
			exc.put(PdfName.CONTENTS, new Integer(estimatedSize * 2 + 2));
			appearance.preClose(exc);

			PdfPKCS7 sgnNew = new PdfPKCS7(null, spec.certificateChain(), spec.hashAlgorithmName(), null, externalDigest, false);

			InputStream data = appearance.getRangeStream();
			byte primeiroHash[] = DigestAlgorithms.digest(data, externalDigest.getMessageDigest(spec.hashAlgorithmName()));

			Calendar cal = Calendar.getInstance();
			sgnNew.setSignDate(cal);
			byte[] authAttrs = sgnNew.getAuthenticatedAttributeBytes(primeiroHash, null, ITextPDFSignatureUtil.crlsToByteCollection(spec.crls()), CryptoStandard.CMS);

			this.appearance = appearance;
			this.firstHash = primeiroHash;
			this.pdfPKCS7 = sgnNew;
			this.signDate = cal;
			this.estimatedSize = estimatedSize;

			return new PreSignature(new AuthenticatedAttributes(authAttrs), new HashToSign(ITextPDFSignatureUtil.applyHash(authAttrs, spec.hashAlgorithmName())), HashType.valueOf(spec.hashAlgorithmName()));
		} catch (IOException e) {
			throw new SignatureException("Erro ler pré-assinatura do PDF..", e);
		} catch (DocumentException e) {
			throw new SignatureException("Erro ao inciar assinatura do PDF.", e);
		} catch (NoSuchAlgorithmException e) {
			throw new SignatureException("Erro ao calcular hash do PDF.", e);
		} catch (InvalidKeyException e) {
			throw new SignatureException("Erro gerar assinatura PKCS7. Chave não encontrada.", e);
		} catch (NoSuchProviderException e) {
			throw new SignatureException("Erro gerar assinatura PKCS7. Provedor não encontrado.", e);
		} catch (CRLException e) {
			throw new SignatureException("Erro ao estimar tamanho da assinatura.", e);
		} catch (GeneralSecurityException e) {
			throw new SignatureException("Erro genérico de segurança.", e);
		}
	}

}
