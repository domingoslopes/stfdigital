package br.jus.stf.plataforma.shared.certification.infra.itext;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;

import br.jus.stf.plataforma.shared.certification.domain.model.PDFSigningSpecification;
import br.jus.stf.plataforma.shared.certification.domain.model.PDFSigningStrategy;
import br.jus.stf.plataforma.shared.certification.domain.model.PreSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.SigningDocument;
import br.jus.stf.plataforma.shared.certification.support.HashSignature;
import br.jus.stf.plataforma.shared.certification.support.SignatureException;

public class ITextPDFSigningStrategy implements PDFSigningStrategy {

	private static final String PDF_EXTENSION = ".pdf";
	private static final String TEMP_FILE_PREFIX = "iTextPDFSigning-";

	private ITextPDFSignatureFinisher finisher;
	
	public ITextPDFSigningStrategy(ITextPDFSignatureFinisher finisher) {
		this.finisher = finisher;
	}
	
	@Override
	public PreSignature preSign(SigningDocument document, PDFSigningSpecification spec) throws SignatureException {
		try {
			PdfReader reader = new PdfReader(document.stream());
			File tempFile = createPDFTempFile();

			PdfStamper stamper = PdfStamper.createSignature(reader, null, '\0', tempFile);
			PdfSignatureAppearance appearance = stamper.getSignatureAppearance();

			appearance.setSignDate(Calendar.getInstance());

			appearance.setReason(spec.reason());

			return finisher.finishPreSignature(spec, appearance);
		} catch (IOException e) {
			throw new SignatureException("Erro ao ler PDF.", e);
		} catch (DocumentException e) {
			throw new SignatureException("Erro ao carregar PDF.", e);
		}
	}

	@Override
	public SigningDocument postSign(HashSignature signature) {
		// TODO Auto-generated method stub
		return null;
	}

	private File createPDFTempFile() throws SignatureException {
		try {
			return File.createTempFile(TEMP_FILE_PREFIX, PDF_EXTENSION);
		} catch (IOException e) {
			throw new SignatureException("Eror ao criar arquivo temporário para assinatura.", e);
		}
	}

}
