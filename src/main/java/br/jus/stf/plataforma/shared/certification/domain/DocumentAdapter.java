package br.jus.stf.plataforma.shared.certification.domain;

import java.io.IOException;

import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

public interface DocumentAdapter {

	byte[] retrieve(DocumentoId id) throws IOException;

	DocumentoTemporarioId upload(String name, byte[] document);

	DocumentoId save(DocumentoTemporarioId tempDocument);

}
