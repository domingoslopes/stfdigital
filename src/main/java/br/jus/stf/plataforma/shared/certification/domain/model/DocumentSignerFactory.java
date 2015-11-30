package br.jus.stf.plataforma.shared.certification.domain.model;

import org.springframework.stereotype.Component;

@Component
public class DocumentSignerFactory {

	public DocumentSigner create(DocumentSignerId signerId, SigningSpecification spec, CertificateValidation validation) {
		return new DocumentSigner(signerId, spec, validation);
	}

}
