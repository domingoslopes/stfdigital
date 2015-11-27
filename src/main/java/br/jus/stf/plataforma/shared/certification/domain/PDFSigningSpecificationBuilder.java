package br.jus.stf.plataforma.shared.certification.domain;

import br.jus.stf.plataforma.shared.certification.domain.model.HashType;
import br.jus.stf.plataforma.shared.certification.domain.model.SigningSpecification;

public interface PDFSigningSpecificationBuilder {

	public interface SpecBuilder {

		public SigningSpecification build();

	}

	public interface PKCS7SpecBuilder extends SpecBuilder {

		public PKCS7SpecBuilder reason(String reason);

		public PKCS7SpecBuilder hashAlgorithm(HashType hashType);

		public SigningSpecification build();

	}

	public PKCS7SpecBuilder pkcs7Dettached();

}
