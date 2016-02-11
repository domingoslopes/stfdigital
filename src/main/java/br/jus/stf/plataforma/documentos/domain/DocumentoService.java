package br.jus.stf.plataforma.documentos.domain;

import java.util.List;

import br.jus.stf.plataforma.documentos.domain.model.ConteudoDocumento;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;

public interface DocumentoService {

	/**
	 * Divide o conteúdo de um documento no intervalo de página inicial e final especificado.
	 * 
	 * @param conteudo
	 * @param paginaInicial
	 * @param paginaFinal
	 * @return
	 */
	DocumentoTemporario dividirConteudo(ConteudoDocumento conteudo, Integer paginaInicial, Integer paginaFinal);

	/**
	 * Une os conteúdos especificados em um documento só.
	 * 
	 * @param conteudos
	 * @return
	 */
	DocumentoTemporario unirConteudos(List<ConteudoDocumento> conteudos);
}
