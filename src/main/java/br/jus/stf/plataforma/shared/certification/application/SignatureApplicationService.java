package br.jus.stf.plataforma.shared.certification.application;

import java.io.IOException;
import java.security.cert.X509Certificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.certification.domain.DocumentAdapter;
import br.jus.stf.plataforma.shared.certification.domain.model.CertificateValidation;
import br.jus.stf.plataforma.shared.certification.domain.model.DocumentSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.DocumentSigner;
import br.jus.stf.plataforma.shared.certification.domain.model.DocumentSignerFactory;
import br.jus.stf.plataforma.shared.certification.domain.model.DocumentSignerRepository;
import br.jus.stf.plataforma.shared.certification.domain.model.PkiId;
import br.jus.stf.plataforma.shared.certification.domain.model.PreSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.SignedDocument;
import br.jus.stf.plataforma.shared.certification.domain.model.SigningDocument;
import br.jus.stf.plataforma.shared.certification.domain.model.SigningSpecification;
import br.jus.stf.plataforma.shared.certification.domain.service.PkiService;
import br.jus.stf.plataforma.shared.certification.signature.DocumentSignerId;
import br.jus.stf.plataforma.shared.certification.support.HashSignature;
import br.jus.stf.plataforma.shared.certification.support.SigningException;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

@Component
public class SignatureApplicationService {

	@Autowired
	private DocumentSignerRepository documentSignerRepository;

	@Autowired
	private PkiService certificateValidationService;

	@Autowired
	private DocumentAdapter documentAdapter;

	@Autowired
	private DocumentSignerFactory signerFactory;

	/**
	 * Recebe o certificado que vai assinar um documento, permitindo a criação
	 * de um assinador de documentos.
	 * 
	 * @param certificate
	 * @param pkiId
	 * @param spec
	 * @return
	 */
	public DocumentSignerId prepareToSign(X509Certificate certificate, PkiId pkiId, SigningSpecification spec)
			throws SigningException {
		CertificateValidation validation = certificateValidationService.validate(certificate, pkiId);
		if (validation.valid()) {
			DocumentSigner signer = signerFactory.create(documentSignerRepository.nextId(), spec, validation);
			documentSignerRepository.save(signer);
			return signer.id();
		} else {
			return null;
		}
	}

	public void attachToSign(DocumentSignerId contextId, SigningDocument document) throws SigningException {
		DocumentSigner signer = documentSignerRepository.findOne(contextId);
		signer.attachDocumentToSign(document);
	}

	public void provideToSign(DocumentSignerId contextId, Long documentId) throws SigningException {
		try {
			SigningDocument document = documentAdapter.retrieve(new DocumentoId(documentId));
			attachToSign(contextId, document);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao recuperar documento.", e);
		}
	}

	public PreSignature preSign(DocumentSignerId signerId) throws SigningException {
		DocumentSigner signer = documentSignerRepository.findOne(signerId);
		PreSignature preSignature = signer.preSign();
		return preSignature;
	}

	public DocumentSignature postSign(DocumentSignerId contextId, HashSignature signature) throws SigningException {
		DocumentSigner signer = documentSignerRepository.findOne(contextId);
		DocumentSignature documentSignature = signer.postSign(signature);
		return documentSignature;
	}

	public SignedDocument recoverSignedDocument(DocumentSignerId signerId) {
		DocumentSigner signer = documentSignerRepository.findOne(signerId);
		SignedDocument document = signer.recoverSignedDocument();
		return document;
	}

	public DocumentoId saveSigned(DocumentSignerId signatureContextId) {
		SignedDocument signedDocument = recoverSignedDocument(signatureContextId);
		DocumentoTemporarioId tempDocId = documentAdapter.upload(signatureContextId.id(), signedDocument);
		DocumentoId docId = documentAdapter.save(tempDocId);
		return docId;
	}

}
