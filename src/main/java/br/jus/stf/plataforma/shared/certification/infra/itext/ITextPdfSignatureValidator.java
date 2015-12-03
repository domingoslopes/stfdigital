package br.jus.stf.plataforma.shared.certification.infra.itext;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.security.PdfPKCS7;

import br.jus.stf.plataforma.shared.certification.domain.model.Document;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.Pki;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.DocumentValidation;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.DocumentValidator;
import br.jus.stf.plataforma.shared.certification.infra.configuration.CryptoProvider;

public class ITextPdfSignatureValidator implements DocumentValidator {

	boolean aceitarUmaAssinaturaValida = true;
	boolean aceitarAssinaturaCertificadoVencido = false;
	boolean aceitarAssinaturaSemCadeia = false;
	boolean verificarCRL = false;
	private Pki[] pkis;

	public ITextPdfSignatureValidator(Pki[] pkis) {
		this.pkis = pkis;
	}

	@Override
	public DocumentValidation validate(Document document) {
		DocumentValidation validation = new DocumentValidation();

		PdfReader reader = null;
		try {
			reader = openPdf(document.stream());

			// Recupera os campos das assinaturas do pdf.
			AcroFields acroFields = reader.getAcroFields();

			Optional.ofNullable(acroFields).orElseThrow(() -> new DocumentValidationException(
					"Não foi possível recuperar os campos de assinatura do pdf."));

			List<String> fields = acroFields.getSignatureNames();

			if (fields == null || fields.size() < 1) {
				throw new DocumentValidationException("Não foi possível recuperar os campos de assinatura do pdf.");
			}

			for (String nomeCampo : fields) {
				PdfPKCS7 pdfPKCS7 = acroFields.verifySignature(nomeCampo, CryptoProvider.provider());

				if (pdfPKCS7 == null) {
					throw new DocumentValidationException("Assinatura não encontrada no pdf.");
				}
			}
		} catch (DocumentValidationException e) {
			validation.appendValidationError(e.getMessage());
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return validation;
	}

	private PdfReader openPdf(InputStream pdf) {
		try {
			return new PdfReader(pdf);
		} catch (IOException e) {
			throw new RuntimeException("Falha ao abrir o pdf.", e);
		}
	}
}
