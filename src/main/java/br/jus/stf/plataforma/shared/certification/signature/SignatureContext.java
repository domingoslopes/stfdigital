package br.jus.stf.plataforma.shared.certification.signature;

import java.io.Serializable;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Collection;
import java.util.UUID;

import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.security.PdfPKCS7;

public final class SignatureContext implements Serializable {

	private static final long serialVersionUID = -4801807132705368529L;
	
	private final SignatureContextId id;
	
	private String idDocumento;
	private PdfSignatureAppearance appearance;
	private byte primeiroHash[];
	private String pdfPath;
	private PdfPKCS7 pdfPKCS7;
	private Calendar signDate;
	private int tamanhoEstimado;
	private Collection<byte[]> crls;

	private X509Certificate[] certificateChain;

	private CRL[] crlsObj;

	public SignatureContext(SignatureContextId id) {
		this.id = id;
	}

	public SignatureContextId signatureContextId() {
		return id;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public PdfSignatureAppearance getAppearance() {
		return appearance;
	}

	public void setAppearance(PdfSignatureAppearance appearance) {
		this.appearance = appearance;
	}

	public byte[] getPrimeiroHash() {
		return primeiroHash;
	}

	public void setPrimeiroHash(byte[] primeiroHash) {
		this.primeiroHash = primeiroHash;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public PdfPKCS7 getPdfPKCS7() {
		return pdfPKCS7;
	}

	public void setPdfPKCS7(PdfPKCS7 pdfPKCS7) {
		this.pdfPKCS7 = pdfPKCS7;
	}

	public Calendar getSignDate() {
		return signDate;
	}

	public void setSignDate(Calendar signDate) {
		this.signDate = signDate;
	}

	public int getTamanhoEstimado() {
		return tamanhoEstimado;
	}

	public void setTamanhoEstimado(int tamanhoEstimado) {
		this.tamanhoEstimado = tamanhoEstimado;
	}

	public Collection<byte[]> getCrls() {
		return crls;
	}

	public void setCrls(Collection<byte[]> crls) {
		this.crls = crls;
	}

	public void attachDocumentToSign(TempDocument tempDocument) {
		pdfPath = tempDocument.tempPath();
	}

	public String reason() {
		return "REASON";
	}

	public Certificate[] certificateChain() {
		return certificateChain;
	}

	public CRL[] crls() {
		return new CRL[]{};
	}

	public void registerCertificateChain(X509Certificate[] chain) {
		certificateChain = chain;
	}

	public void registerCrls(CRL[] crls) {
		this.crlsObj = crls;
	}
	
}