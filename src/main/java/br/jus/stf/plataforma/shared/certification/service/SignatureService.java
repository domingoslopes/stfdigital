package br.jus.stf.plataforma.shared.certification.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.X509Certificate;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.shared.certification.Pki;
import br.jus.stf.plataforma.shared.certification.signature.PreSignature;
import br.jus.stf.plataforma.shared.certification.signature.SignatureContext;
import br.jus.stf.plataforma.shared.certification.signature.SignatureContextId;
import br.jus.stf.plataforma.shared.certification.signature.SignedDocument;
import br.jus.stf.plataforma.shared.certification.signature.StreamedDocument;
import br.jus.stf.plataforma.shared.certification.signature.TempDocument;
import br.jus.stf.plataforma.shared.certification.support.AssinadorPorPartes;
import br.jus.stf.plataforma.shared.certification.support.AssinaturaExternaException;
import br.jus.stf.plataforma.shared.certification.support.AuthenticatedAttributes;
import br.jus.stf.plataforma.shared.certification.support.HashSignature;
import br.jus.stf.plataforma.shared.certification.support.HashToSign;
import br.jus.stf.plataforma.shared.certification.support.HashType;
import br.jus.stf.plataforma.shared.certification.support.SHA256DetachedAssinadorPorPartes;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

@Service
public class SignatureService {

	@Autowired
	private SignatureContextManager contextManager;

	@Autowired
	private ValidationService validationService;
	
	@Autowired
	private DocumentAdapter documentAdapter;

	/**
	 * Recebe o certificado que vai assinar um documento, permitindo a criação
	 * de um contexto de assinatura.
	 * 
	 * @param certificate
	 * @return
	 */
	public SignatureContextId prepareToSign(X509Certificate certificate, Pki pki) {
		if (validationService.validate(certificate, pki)) {
			return contextManager.generateSignatureContext(new X509Certificate[] { certificate });
		} else {
			return null;
		}
	}

	public void attachToSign(SignatureContextId contextId, StreamedDocument document) {
		SignatureContext context = contextManager.recoverSignatureContext(contextId);
		context.attachDocumentToSign(new TempDocument(document));
	}

	public void provideToSign(SignatureContextId contextId, Long documentId) {
		try {
			byte[] document = documentAdapter.retrieve(new DocumentoId(documentId));
			attachToSign(contextId, new StreamedDocument(new ByteArrayInputStream(document)));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao recuperar documento.", e);
		}
	}
	
	public PreSignature preSign(SignatureContextId contextId) throws AssinaturaExternaException {
		SignatureContext context = contextManager.recoverSignatureContext(contextId);
		AssinadorPorPartes app = new SHA256DetachedAssinadorPorPartes(false);
		byte[] auth = app.preAssinar(context);
		byte[] hash = app.prepararHashParaAssinaturaExterna(auth);
		return new PreSignature(new AuthenticatedAttributes(auth), new HashToSign(hash), HashType.SHA256);
	}

	public void postSign(SignatureContextId contextId, HashSignature signature) throws AssinaturaExternaException {
		SignatureContext context = contextManager.recoverSignatureContext(contextId);
		AssinadorPorPartes app = new SHA256DetachedAssinadorPorPartes(false);
		app.posAssinar(context, signature);
	}
	
	public SignedDocument recoverSignedDocument(SignatureContextId contextId) {
		SignatureContext context = contextManager.recoverSignatureContext(contextId);
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

	public DocumentoId saveSigned(SignatureContextId signatureContextId) {
		SignedDocument signedDocument = recoverSignedDocument(signatureContextId);
		DocumentoTemporarioId tempDocId = documentAdapter.upload(signatureContextId.id(), signedDocument.asBytes());
		DocumentoId docId = documentAdapter.save(tempDocId);
		return docId;
	}

}
