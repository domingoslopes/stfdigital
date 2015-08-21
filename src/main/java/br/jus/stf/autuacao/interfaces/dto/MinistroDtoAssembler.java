/**
 * 
 */
package br.jus.stf.autuacao.interfaces.dto;

import br.jus.stf.autuacao.domain.entity.Ministro;


/**
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 23.07.2015
 */
public class MinistroDtoAssembler {
	public MinistroDto toDto(Ministro ministro ) {
		return new MinistroDto(String.valueOf(ministro.getId()), ministro.getNome());
	}
}
