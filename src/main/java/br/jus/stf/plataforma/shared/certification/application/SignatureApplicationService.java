package br.jus.stf.plataforma.shared.certification.application;

import java.io.IOException;
import java.security.cert.X509Certificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.certification.domain.DocumentAdapter;
import br.jus.stf.plataforma.shared.certification.domain.model.Document;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.PkiIds;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.DocumentSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.DocumentSigner;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.DocumentSignerFactory;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.DocumentSignerId;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.DocumentSignerRepository;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.HashSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.PreSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.SignedDocument;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.SigningException;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.SigningSpecification;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.CertificateValidation;
import br.jus.stf.plataforma.shared.certification.domain.service.CertificateValidationService;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

@Component
public class SignatureApplicationService {

	@Autowired
	private DocumentSignerRepository documentSignerRepository;

	@Autowired
	private CertificateValidationService certificateValidationService;

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
	public DocumentSignerId prepareToSign(X509Certificate certificate, PkiIds pkiIds, SigningSpecification spec)
			throws SigningException {
		CertificateValidation validation = certificateValidationService.validate(certificate, pkiIds);
		if (validation.valid()) {
			DocumentSigner signer = signerFactory.create(documentSignerRepository.nextId(), spec, validation);
			documentSignerRepository.save(signer);
			return signer.id();
		} else {
			return null;
		}
	}

	public void attachToSign(DocumentSignerId signerId, Document document) throws SigningException {
		DocumentSigner signer = documentSignerRepository.findOne(signerId);
		signer.attachDocumentToSign(document);
	}

	public void provideToSign(DocumentSignerId signerId, Long documentId) throws SigningException {
		try {
			Document document = documentAdapter.retrieve(new DocumentoId(documentId));
			attachToSign(signerId, document);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao recuperar documento.", e);
		}
	}

	public PreSignature preSign(DocumentSignerId signerId) throws SigningException {
		DocumentSigner signer = documentSignerRepository.findOne(signerId);
		return signer.preSign();
	}

	public DocumentSignature postSign(DocumentSignerId signerId, HashSignature signature) throws SigningException {
		DocumentSigner signer = documentSignerRepository.findOne(signerId);
		return signer.postSign(signature);
	}

	public SignedDocument recoverSignedDocument(DocumentSignerId signerId) {
		DocumentSigner signer = documentSignerRepository.findOne(signerId);
		return signer.recoverSignedDocument();
	}

	public DocumentoId saveSigned(DocumentSignerId signerId) {
		SignedDocument signedDocument = recoverSignedDocument(signerId);
		DocumentoTemporarioId tempDocId = documentAdapter.upload(signerId.id(), signedDocument.document());
		return documentAdapter.save(tempDocId);
	}

}
