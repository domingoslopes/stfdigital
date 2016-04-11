package br.jus.stf.plataforma.documentos.infra;

import br.jus.stf.plataforma.documentos.domain.ContadorPaginas;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;

public class ContadorPaginasDocx implements ContadorPaginas {

	/**
	 * Não conta as páginas do Docx, pois o Onlyoffice não preenche esse
	 * metadado, diferentemente do Word.
	 */
	@Override
	public Integer contarPaginas(DocumentoTemporario docTemp) {
		return null;
	}

}
