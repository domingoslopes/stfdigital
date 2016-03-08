package br.jus.stf.plataforma.documentos.infra;

import br.jus.stf.plataforma.documentos.domain.ContadorPaginas;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.plataforma.shared.strategy.StrategyResolver;

public class ContadorPaginasImpl extends StrategyResolver<String, ContadorPaginas> implements ContadorPaginas {

	@Override
	public Integer contarPaginas(DocumentoTemporario docTemp) {
		return resolveStrategy(docTemp.contentType()).contarPaginas(docTemp);
	}

}
