package br.jus.stf.plataforma.shared.certification.infra;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.certification.domain.DocumentValidatorFactory;
import br.jus.stf.plataforma.shared.certification.domain.model.DocumentValidator;
import br.jus.stf.plataforma.shared.certification.domain.model.Pki;
import br.jus.stf.plataforma.shared.certification.infra.itext.ITextPDFSignatureValidator;

@Component
public class DocumentValidatorFactoryImpl implements DocumentValidatorFactory {

	@Override
	public DocumentValidator createDocumentSignatureValidator(Pki[] pkis) {
		return new ITextPDFSignatureValidator(pkis);
	}

}
