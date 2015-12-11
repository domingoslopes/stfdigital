package br.jus.stf.plataforma.shared.certification.interfaces.validators;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.certification.application.DocumentValidatorApplicationService;
import br.jus.stf.plataforma.shared.certification.domain.PdfInputStreamDocument;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.PkiIds;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.PkiType;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.DocumentValidation;

@Component
public abstract class GenericSignatureValidator {

	@Autowired
	protected DocumentValidatorApplicationService documentValidatorApplicationService;
	
	public void initialize(SignedDocument constraintAnnotation) {
		
	}

	public boolean isValid(InputStream value) {
		DocumentValidation validation = documentValidatorApplicationService.validateDocumentSignature(new PdfInputStreamDocument(value), new PkiIds(PkiType.ICP_BRASIL.id(), PkiType.ICP_PLATAFORMA.id()));
		return validation.valid();
	}
	
}
