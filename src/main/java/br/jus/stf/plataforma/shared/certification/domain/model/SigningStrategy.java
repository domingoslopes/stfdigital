package br.jus.stf.plataforma.shared.certification.domain.model;

public interface SigningStrategy {

	PreSignature preSign(SigningDocument document, CertificateValidation certificateValidation) throws SigningException;

	SignedDocument postSign(HashSignature signature, CertificateValidation certificateValidation)
			throws SigningException;

}
