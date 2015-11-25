package br.jus.stf.plataforma.shared.certification.infra.itext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;

import br.jus.stf.plataforma.shared.certification.domain.model.CertificateValidation;
import br.jus.stf.plataforma.shared.certification.domain.model.DocumentSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.PDFSigningSpecification;
import br.jus.stf.plataforma.shared.certification.domain.model.PDFSigningStrategy;
import br.jus.stf.plataforma.shared.certification.domain.model.PreSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.SignedDocument;
import br.jus.stf.plataforma.shared.certification.domain.model.SigningDocument;
import br.jus.stf.plataforma.shared.certification.infra.PdfTempDocument;
import br.jus.stf.plataforma.shared.certification.infra.PdfTempSignedDocument;
import br.jus.stf.plataforma.shared.certification.support.HashSignature;
import br.jus.stf.plataforma.shared.certification.support.SigningException;

public class ITextPDFSigningStrategy implements PDFSigningStrategy {

	private static final String PDF_EXTENSION = ".pdf";
	private static final String TEMP_FILE_PREFIX = "iTextPDFSigning-";

	private ITextPDFSignatureFinisher finisher;

	private PDFSigningSpecification spec;

	private PdfSignatureAppearance appearance;

	public ITextPDFSigningStrategy(ITextPDFSignatureFinisher finisher) {
		this.finisher = finisher;
	}

	@Override
	public void prepareStrategyWith(PDFSigningSpecification spec) {
		this.spec = spec;
	}

	@Override
	public PreSignature preSign(SigningDocument document, CertificateValidation certificateValidation)
			throws SigningException {
		try {
			PdfReader reader = new PdfReader(document.stream());
			File tempFile = createPDFTempFile();

			PdfStamper stamper = PdfStamper.createSignature(reader, null, '\0', tempFile);
			appearance = stamper.getSignatureAppearance();

			appearance.setSignDate(Calendar.getInstance());

			appearance.setReason(spec.reason());

			return finisher.finishPreSignature(spec, certificateValidation, appearance);
		} catch (IOException e) {
			throw new SigningException("Erro ao ler PDF.", e);
		} catch (DocumentException e) {
			throw new SigningException("Erro ao carregar PDF.", e);
		}
	}

	@Override
	public SignedDocument postSign(HashSignature signature, CertificateValidation certificateValidation) throws SigningException {
		finisher.finishPostSignature(spec, certificateValidation, appearance, signature);

		SignedDocument document = new PdfTempSignedDocument(appearance.getTempFile(), new DocumentSignature());
		
		return document;
	}

	private File createPDFTempFile() throws SigningException {
		try {
			return File.createTempFile(TEMP_FILE_PREFIX, PDF_EXTENSION);
		} catch (IOException e) {
			throw new SigningException("Eror ao criar arquivo temporário para assinatura.", e);
		}
	}

}
