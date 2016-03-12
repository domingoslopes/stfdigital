package br.jus.stf.plataforma.documentos.domain;

import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.shared.DocumentoId;

public interface ConversorDocumentoService {

	DocumentoTemporario converterDocumentoFinal(DocumentoId documentoId);

}
