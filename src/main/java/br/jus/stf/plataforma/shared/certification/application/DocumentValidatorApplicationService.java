package br.jus.stf.plataforma.shared.certification.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.certification.domain.DocumentValidatorFactory;
import br.jus.stf.plataforma.shared.certification.domain.model.Document;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.Pki;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.PkiIds;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.PkiRepository;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.DocumentValidation;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.DocumentValidator;

@Component
public class DocumentValidatorApplicationService {

	@Autowired
	private DocumentValidatorFactory factory;
	
	@Autowired
	private PkiRepository pkiRepository;
	
	public DocumentValidation validateDocumentSignature(Document document, PkiIds pkiIds) {
		Pki[] pkis = pkiRepository.findAll(pkiIds.ids());
		DocumentValidator validator = factory.createDocumentSignatureValidator(pkis);
		DocumentValidation validation = validator.validate(document);
		return validation;
	}
	
}
