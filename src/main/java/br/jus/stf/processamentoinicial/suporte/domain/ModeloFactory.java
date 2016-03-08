package br.jus.stf.processamentoinicial.suporte.domain;

import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.TipoModeloId;

/**
 * Factory de Modelo.
 * 
 * @author Tomas.Godoi
 *
 */
public interface ModeloFactory {

	/**
	 * Cria um modelo a partir do template padrão.
	 * 
	 * @param tipoModelo Tipo do Modelo
	 * @param nome Nome do modelo
	 * @return
	 */
	DocumentoId criarDocumentoModeloPadrao(TipoModeloId tipoModelo, String nome);

}
