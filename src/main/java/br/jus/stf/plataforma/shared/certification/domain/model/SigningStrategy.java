package br.jus.stf.plataforma.shared.certification.domain.model;

import br.jus.stf.plataforma.shared.certification.support.HashSignature;
import br.jus.stf.plataforma.shared.certification.support.SigningException;

public interface SigningStrategy {

	PreSignature preSign(SigningDocument document, CertificateValidation certificateValidation) throws SigningException;

	SignedDocument postSign(HashSignature signature, CertificateValidation certificateValidation)
			throws SigningException;

}
