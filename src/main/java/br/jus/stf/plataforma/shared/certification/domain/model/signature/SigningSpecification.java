package br.jus.stf.plataforma.shared.certification.domain.model.signature;

import br.jus.stf.plataforma.shared.certification.domain.model.DocumentType;

public interface SigningSpecification {

	DocumentType documentType();

	SigningStrategy strategy();

}
