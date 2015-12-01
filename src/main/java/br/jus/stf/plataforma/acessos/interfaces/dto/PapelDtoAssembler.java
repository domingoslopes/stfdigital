/**
 * 
 */
package br.jus.stf.plataforma.acessos.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.acessos.domain.model.Papel;

/**
 * 
 * @author Anderson.Araujo
 * 
 * @since 30.11.2015
 *
 */
@Component
public class PapelDtoAssembler {
	
	public PapelDto toDto(Papel papel) {
		return new PapelDto(papel.nome(), "");
	}
}
