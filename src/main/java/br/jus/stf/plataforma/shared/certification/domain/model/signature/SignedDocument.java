package br.jus.stf.plataforma.shared.certification.domain.model.signature;

import br.jus.stf.plataforma.shared.certification.domain.model.Document;

public interface SignedDocument {
	
	Document document();
	
	DocumentSignature signature();

}
