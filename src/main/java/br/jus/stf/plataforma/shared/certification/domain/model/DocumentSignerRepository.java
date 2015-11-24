package br.jus.stf.plataforma.shared.certification.domain.model;

import br.jus.stf.plataforma.shared.certification.signature.DocumentSignerId;

public interface DocumentSignerRepository {

	public DocumentSigner save(DocumentSigner signer);

	public DocumentSigner findOne(DocumentSignerId signerId);

	public DocumentSignerId nextId();
}