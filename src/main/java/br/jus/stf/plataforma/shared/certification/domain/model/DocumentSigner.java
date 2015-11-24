package br.jus.stf.plataforma.shared.certification.domain.model;

import br.jus.stf.plataforma.shared.certification.signature.DocumentSignerId;
import br.jus.stf.plataforma.shared.certification.support.HashSignature;
import br.jus.stf.plataforma.shared.certification.support.SigningException;

public class DocumentSigner {

	private DocumentSignerId id;
	private SigningSpecification spec;
	private CertificateValidation certificateValidation;

	private SigningDocument signingDocument;
	private SignedDocument signedDocument;

	public DocumentSigner(DocumentSignerId id, SigningSpecification spec, CertificateValidation certificateValidation) {
		this.id = id;
		this.spec = spec;
		this.certificateValidation = certificateValidation;
	}

	public DocumentSignerId id() {
		return id;
	}

	public PreSignature preSign() throws SigningException {
		return spec.strategy().preSign(signingDocument, certificateValidation);
	}

	public void attachDocumentToSign(SigningDocument document) {
		this.signingDocument = document;
	}

	public DocumentSignature postSign(HashSignature signature) throws SigningException {
		signedDocument = spec.strategy().postSign(signature, certificateValidation);
		return signedDocument.signature();
	}

	public SignedDocument recoverSignedDocument() {
		return signedDocument;
	}

}
