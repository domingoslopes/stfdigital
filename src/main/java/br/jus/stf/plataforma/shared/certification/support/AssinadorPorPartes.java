package br.jus.stf.plataforma.shared.certification.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;

import br.jus.stf.plataforma.shared.certification.signature.SignatureContext;

public abstract class AssinadorPorPartes {

	private static final String EXTENSAO_PDF = ".pdf";

	protected boolean hasTSA;
	protected boolean hasOCSP;

	public AssinadorPorPartes(boolean hasTSA) {
		this.hasTSA = hasTSA;
		this.hasOCSP = false;
	}

	protected abstract byte[] preGerarHashes(Certificate[] cadeia, CRL[] crls, SignatureContext ca, PdfStamper stamper, PdfSignatureAppearance appearance)
			throws IOException, DocumentException, SignatureException;

	public abstract byte[] prepararHashParaAssinaturaExterna(byte[] dataToSign);

	public byte[] preAssinar(SignatureContext ca) throws SignatureException {
		try {
			PdfReader reader = new PdfReader(ca.getPdfPath());
			File arquivoTemporario = criaArquivoTemporarioParaPdfAssinado(ca);

			PdfStamper stamper = PdfStamper.createSignature(reader, null, '\0', arquivoTemporario);
			PdfSignatureAppearance appearance = stamper.getSignatureAppearance();

			appearance.setSignDate(Calendar.getInstance());
			
			appearance.setReason(ca.reason());

			byte[] hashParaAssinar = preGerarHashes(ca.certificateChain(), ca.crls(), ca, stamper, appearance);

			return hashParaAssinar;
		} catch (IOException e) {
			throw new SignatureException("Erro ao ler PDF.", e);
		} catch (DocumentException e) {
			throw new SignatureException("Erro ao carregar PDF.", e);
		}
	}

	protected abstract void posAssinarImpl(SignatureContext ca, PdfSignatureAppearance appearance, byte[] primeiroHash, HashSignature assinatura)
			throws SignatureException;

	public void posAssinar(SignatureContext ca, HashSignature assinatura) throws SignatureException {
		InputStream is = null;
//		try {
			PdfSignatureAppearance appearance = ca.getAppearance();
			byte primeiroHash[] = ca.getPrimeiroHash();

			posAssinarImpl(ca, appearance, primeiroHash, assinatura);
//			is = new FileInputStream(appearance.getTempFile());
//			byte[] pdfAssinado = IOUtils.toByteArray(is);

//			return pdfAssinado;
			
			String signedFilePath = appearance.getTempFile().getAbsolutePath();
			ca.setSignedFilePath(signedFilePath);
//		} catch (IOException e) {
//			throw new AssinaturaExternaException("Erro ao finalizar montagem do PDF.", e);
		/*} finally {
			IOUtils.closeQuietly(is);
		}*/
	}

	/**
	 * Extrai a assinatura mais recente do Pdf, caso exista mais de uma.
	 * 
	 * @param pdfAssinado
	 * @return
	 * @throws AssinadorException
	 */
	public byte[] extrairAssinatura(byte[] pdfAssinado) throws SignatureException {
		try {
			AcroFields campos = new PdfReader(pdfAssinado).getAcroFields();
			byte[] assinatura = identificaAssinaturaMaisRecente(campos);
			if (assinatura.length == 0) {
				throw new Exception("O conte�do da assinatura est� vazio!");
			}
			return assinatura;
		} catch (Exception e) {
			throw new SignatureException("Erro ao extrair assinatura do PDF assinado.", e);
		}
	}

	private byte[] identificaAssinaturaMaisRecente(AcroFields campos) {
		byte[] assinatura = null;
		PdfString dataEscolhida = null;
		List<String> nomesCampos = campos.getSignatureNames();
		for (String idAssinatura : nomesCampos) {
			PdfDictionary dicionarioPDF = campos.getSignatureDictionary(idAssinatura);
			// O item PdfName.M traz a data em que o documento foi assinado
			PdfString dataAssinatura = (PdfString) dicionarioPDF.get(PdfName.M);
			if (isDataEscolhidaMenorQueAssinatura(dataEscolhida, dataAssinatura)) {
				dataEscolhida = dataAssinatura;
				assinatura = recuperaDadosDaAssinatura(dicionarioPDF);
			}
		}
		return assinatura;
	}

	private byte[] recuperaDadosDaAssinatura(PdfDictionary dicionarioPDF) {
		PdfString conteudo = (PdfString) dicionarioPDF.get(PdfName.CONTENTS);
		return conteudo.getBytes();
	}

	private boolean isDataEscolhidaMenorQueAssinatura(PdfString dataEscolhida, PdfString dataAssinatura) {
		if (dataEscolhida == null) {
			return true;
		}
		return dataEscolhida.toString().compareTo(dataAssinatura.toString()) < 0;
	}

	private File criaArquivoTemporarioParaPdfAssinado(SignatureContext ca) throws SignatureException {
		try {
			File arquivoTemporario = File.createTempFile(ca.signatureContextId().id(), EXTENSAO_PDF);
			return arquivoTemporario;
		} catch (IOException e) {
			throw new SignatureException("Eror ao criar arquivo tempor�rio para assinatura.", e);
		}
	}

	protected int calcularTamanhoEstimado(CRL[] crls) throws CRLException {
		int tamanhoEstimado = 8192;

		if (crls != null && crls.length > 0) {
			for (CRL crl : crls) {
				X509CRL xCrl = (X509CRL) crl;
				tamanhoEstimado += xCrl.getEncoded().length + 10;
			}
		}

		if (hasOCSP) {
			tamanhoEstimado += 4192;
		}

		if (hasTSA) {
			tamanhoEstimado += 4192;
		}

		return tamanhoEstimado;
	}

}
