package br.jus.stf.plataforma.documentos.domain;

import java.io.RandomAccessFile;
import java.util.List;

import org.apache.commons.lang3.Range;

import br.jus.stf.plataforma.documentos.domain.model.ConteudoDocumento;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

public interface DocumentoService {

	/**
	 * Realiza a contagem da quantidade de páginas em um arquivo PDF.
	 * 
	 * @param file
	 * @return
	 */
	public Integer contarPaginas(RandomAccessFile file);
	
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
	
	/**
	 * Divide um documento nos intervalos especificados.
	 * 
	 * @param id
	 * @param intervalos
	 * @return
	 */
	List<DocumentoTemporarioId> dividirDocumento(DocumentoId id, List<Range<Integer>> intervalos);
	
	/**
	 * Divide um documento contiguamente como especificado pelos intervalos.
	 * 
	 * @param id
	 * @param intervalos
	 * @return
	 */
	public List<DocumentoTemporarioId> dividirDocumentoContiguamente(DocumentoId id, List<Range<Integer>> intervalos);
}
