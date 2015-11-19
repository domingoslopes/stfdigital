package br.jus.stf.plataforma.shared.certification.service;

import java.security.cert.X509Certificate;

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

@Service
public class SignatureService {

	@Autowired
	private SignatureContextManager contextManager;

	@Autowired
	private ValidationService validationService;

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

	public PreSignature preSign(SignatureContextId contextId) throws AssinaturaExternaException {
		SignatureContext context = contextManager.recoverSignatureContext(contextId);
		AssinadorPorPartes app = new SHA256DetachedAssinadorPorPartes(false);
		byte[] auth = app.preAssinar(context);
		byte[] hash = app.prepararHashParaAssinaturaExterna(auth);
		return new PreSignature(new AuthenticatedAttributes(auth), new HashToSign(hash), HashType.SHA256);
	}

	public SignedDocument postSign(SignatureContextId contextId, HashSignature signature) throws AssinaturaExternaException {
		SignatureContext context = contextManager.recoverSignatureContext(contextId);
		AssinadorPorPartes app = new SHA256DetachedAssinadorPorPartes(false);
		byte[] pdf = app.posAssinar(context, signature);
		return new SignedDocument(pdf);
	}

}
