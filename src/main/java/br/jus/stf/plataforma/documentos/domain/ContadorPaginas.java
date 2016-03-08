package br.jus.stf.plataforma.documentos.domain;

import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.plataforma.shared.strategy.Strategy;

public interface ContadorPaginas extends Strategy<ContadorPaginas> {

	Integer contarPaginas(DocumentoTemporario docTemp);

}
