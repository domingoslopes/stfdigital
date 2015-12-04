package br.jus.stf.plataforma.shared.certification.domain.model.validation;

import br.jus.stf.plataforma.shared.certification.domain.model.Document;

public interface DocumentValidator {

	DocumentValidation validate(Document document);
	
}
