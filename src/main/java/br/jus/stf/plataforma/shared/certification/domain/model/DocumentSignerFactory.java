package br.jus.stf.plataforma.shared.certification.domain.model;

import java.security.cert.X509Certificate;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.certification.Pki;
import br.jus.stf.plataforma.shared.certification.signature.DocumentSignerId;

@Component
public class DocumentSignerFactory {

	public DocumentSigner create(DocumentSignerId documentSignerId, X509Certificate certificate, Pki pki, SigningSpecification spec) {
		return null;
	}

}
