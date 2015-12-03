package br.jus.stf.plataforma.shared.certification.domain;

import br.jus.stf.plataforma.shared.certification.domain.model.pki.Pki;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.DocumentValidator;

public interface DocumentValidatorFactory {

	DocumentValidator createDocumentSignatureValidator(Pki pki);

	DocumentValidator createDocumentSignatureValidator(Pki[] pkis);

}
