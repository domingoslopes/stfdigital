package br.jus.stf.plataforma.shared.certification.infra.session;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.jus.stf.plataforma.shared.certification.domain.model.DocumentSigner;
import br.jus.stf.plataforma.shared.certification.domain.model.DocumentSignerId;
import br.jus.stf.plataforma.shared.certification.domain.model.DocumentSignerRepository;

@Component
public class SessionDocumentSignerRepository implements DocumentSignerRepository {

	public DocumentSigner save(DocumentSigner signer) {
		DocumentSignerId signerId = signer.id();
		session().setAttribute(signerId.id(), signer);
		return signer;
	}

	public DocumentSigner findOne(DocumentSignerId signerId) {
		DocumentSigner signer = (DocumentSigner) session().getAttribute(signerId.id());
		if (signer != null) {
			return signer;
		} else {
			return null;
		}
	}

	public HttpSession session() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return sra.getRequest().getSession();
	}

	public DocumentSignerId nextId() {
		return new DocumentSignerId(UUID.randomUUID().toString());
	}
}
