package br.jus.stf.plataforma.shared.certification.domain.model;

import br.jus.stf.plataforma.shared.certification.support.HashSignature;
import br.jus.stf.plataforma.shared.certification.support.SignatureException;

public interface SigningStrategy<T extends SigningSpecification> {

	PreSignature preSign(SigningDocument document, T spec) throws SignatureException;
	
	SigningDocument postSign(HashSignature signature) throws SignatureException;
	
}
