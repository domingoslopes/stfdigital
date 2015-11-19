package br.jus.stf.plataforma.shared.certification.service;

import java.security.cert.X509Certificate;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.jus.stf.plataforma.shared.certification.signature.SignatureContext;
import br.jus.stf.plataforma.shared.certification.signature.SignatureContextId;

@Service
public class SignatureContextManager {

	public SignatureContextId generateSignatureContext(X509Certificate[] chain) {
		SignatureContextId contextId = new SignatureContextId(UUID.randomUUID().toString());
		SignatureContext context = new SignatureContext(contextId);
		context.registerCertificateChain(chain);
		getSession().setAttribute(contextId.id(), context);
		return contextId;
	}

	public SignatureContext recoverSignatureContext(SignatureContextId contextId) {
		SignatureContext context = (SignatureContext) getSession().getAttribute(contextId.id());
		if (context != null) {
			return context;
		} else {
			return null;
		}
	}

	public HttpSession getSession() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return sra.getRequest().getSession();
	}
}
