package br.jus.stf.plataforma.shared.certification.domain;

import br.jus.stf.plataforma.shared.certification.domain.model.DocumentValidator;
import br.jus.stf.plataforma.shared.certification.domain.model.Pki;

public interface DocumentValidatorFactory {

	DocumentValidator createDocumentSignatureValidator(Pki[] pkis);

}
