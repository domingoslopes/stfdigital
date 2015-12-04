package br.jus.stf.plataforma.shared.certification.domain.model.signature;

import br.jus.stf.plataforma.shared.certification.domain.model.Document;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.CertificateValidation;

public interface SigningStrategy {

	PreSignature preSign(Document document, CertificateValidation certificateValidation) throws SigningException;

	SignedDocument postSign(HashSignature signature, CertificateValidation certificateValidation)
			throws SigningException;

}
