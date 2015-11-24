package br.jus.stf.plataforma.shared.certification.application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.X509Certificate;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.certification.Pki;
import br.jus.stf.plataforma.shared.certification.domain.DocumentAdapter;
import br.jus.stf.plataforma.shared.certification.domain.model.DocumentSigner;
import br.jus.stf.plataforma.shared.certification.domain.model.DocumentSignerFactory;
import br.jus.stf.plataforma.shared.certification.domain.model.DocumentSignerRepository;
import br.jus.stf.plataforma.shared.certification.domain.model.HashToSign;
import br.jus.stf.plataforma.shared.certification.domain.model.HashType;
import br.jus.stf.plataforma.shared.certification.domain.model.PreSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.SigningDocument;
import br.jus.stf.plataforma.shared.certification.domain.model.SigningSpecification;
import br.jus.stf.plataforma.shared.certification.domain.service.ValidationService;
import br.jus.stf.plataforma.shared.certification.signature.DocumentSignerId;
import br.jus.stf.plataforma.shared.certification.signature.SignatureContext;
import br.jus.stf.plataforma.shared.certification.signature.SignedDocument;
import br.jus.stf.plataforma.shared.certification.signature.StreamedDocument;
import br.jus.stf.plataforma.shared.certification.signature.TempDocument;
import br.jus.stf.plataforma.shared.certification.support.AssinadorPorPartes;
import br.jus.stf.plataforma.shared.certification.support.SignatureException;
import br.jus.stf.plataforma.shared.certification.support.AuthenticatedAttributes;
import br.jus.stf.plataforma.shared.certification.support.HashSignature;
import br.jus.stf.plataforma.shared.certification.support.SHA256DetachedAssinadorPorPartes;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

@Component
public class SignatureApplicationService {

	@Autowired
	private DocumentSignerRepository documentSignerRepository;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private DocumentAdapter documentAdapter;

	@Autowired
	private DocumentSignerFactory signerFactory;

	/**
	 * Recebe o certificado que vai assinar um documento, permitindo a criação
	 * de um contexto de assinatura.
	 * 
	 * @param certificate
	 * @return
	 */
	public DocumentSignerId prepareToSign(X509Certificate certificate, Pki pki, SigningSpecification spec) {
		if (validationService.validate(certificate, pki)) {
			DocumentSigner signer = signerFactory.create(documentSignerRepository.nextId(), certificate, pki, spec);
			documentSignerRepository.save(signer);
			return signer.id();
		} else {
			return null;
		}
	}

	public void attachToSign(DocumentSignerId contextId, StreamedDocument document) {
		DocumentSigner signer = documentSignerRepository.findOne(contextId);
		signer.attachDocumentToSign(new TempDocument(document));
	}

	public void provideToSign(DocumentSignerId contextId, Long documentId) {
		try {
			byte[] document = documentAdapter.retrieve(new DocumentoId(documentId));
			attachToSign(contextId, new StreamedDocument(new ByteArrayInputStream(document)));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao recuperar documento.", e);
		}
	}

	public PreSignature preSign(DocumentSignerId signerId) throws SignatureException {
		DocumentSigner signer = documentSignerRepository.findOne(signerId);
		SigningDocument document = new SigningDocument();
		PreSignature preSignature = signer.preSign(document);
		return preSignature;
//		SignatureContext context = documentSignerRepository.findOne(contextId);
//		AssinadorPorPartes app = new SHA256DetachedAssinadorPorPartes(false);
//		byte[] auth = app.preAssinar(context);
//		byte[] hash = app.prepararHashParaAssinaturaExterna(auth);
//		return new PreSignature(new AuthenticatedAttributes(auth), new HashToSign(hash), HashType.SHA256);
	}

	public void postSign(DocumentSignerId contextId, HashSignature signature) throws SignatureException {
		SignatureContext context = documentSignerRepository.findOne(contextId);
		AssinadorPorPartes app = new SHA256DetachedAssinadorPorPartes(false);
		app.posAssinar(context, signature);
	}

	public SignedDocument recoverSignedDocument(DocumentSignerId contextId) {
		SignatureContext context = documentSignerRepository.findOne(contextId);
		FileInputStream is = null;
		try {
			is = new FileInputStream(new File(context.signedFilePath()));
			byte[] signedDocumentBytes = IOUtils.toByteArray(is);
			return new SignedDocument(signedDocumentBytes);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao recuperar documento assinado.", e);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public DocumentoId saveSigned(DocumentSignerId signatureContextId) {
		SignedDocument signedDocument = recoverSignedDocument(signatureContextId);
		DocumentoTemporarioId tempDocId = documentAdapter.upload(signatureContextId.id(), signedDocument.asBytes());
		DocumentoId docId = documentAdapter.save(tempDocId);
		return docId;
	}

}
