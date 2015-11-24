package br.jus.stf.plataforma.shared.certification.domain;

import java.io.IOException;

import br.jus.stf.plataforma.shared.certification.domain.model.SignedDocument;
import br.jus.stf.plataforma.shared.certification.domain.model.SigningDocument;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

public interface DocumentAdapter {

	SigningDocument retrieve(DocumentoId id) throws IOException;

	DocumentoTemporarioId upload(String name, SignedDocument document);

	DocumentoId save(DocumentoTemporarioId tempDocument);

}
