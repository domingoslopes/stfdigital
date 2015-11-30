package br.jus.stf.plataforma.shared.certification.infra.itext;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.certification.domain.PDFSigningSpecificationBuilder;
import br.jus.stf.plataforma.shared.certification.domain.model.HashType;
import br.jus.stf.plataforma.shared.certification.domain.model.PDFSigningSpecification;
import br.jus.stf.plataforma.shared.certification.domain.model.PDFSigningStrategy;
import br.jus.stf.plataforma.shared.certification.domain.model.SigningSpecification;

@Component
public class ITextPDFSigningSpecificationBuilder implements PDFSigningSpecificationBuilder {

	public class ITextPKCS7SpecBuilder implements PKCS7SpecBuilder {

		private String reason;
		private HashType hashType;

		public PKCS7SpecBuilder reason(String reason) {
			this.reason = reason;
			return this;
		}

		public PKCS7SpecBuilder hashAlgorithm(HashType hashType) {
			this.hashType = hashType;
			return this;
		}

		public SigningSpecification build() {
			PDFSigningStrategy strategy = new ITextPDFSigningStrategy(new PKCS7DettachedITextPDFSignatureFinisher());
			return new PDFSigningSpecification(strategy, reason, hashType);
		}

	}

	private PKCS7SpecBuilder pkcs7SpecBuilder = new ITextPKCS7SpecBuilder();

	public PKCS7SpecBuilder pkcs7Dettached() {
		return pkcs7SpecBuilder;
	}

}
