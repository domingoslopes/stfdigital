package br.jus.stf.plataforma.acessos.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.acessos.domain.model.Grupo;

/**
 * Transforma um Grupo num GrupoDto.
 * 
 * @author Anderson.Araujo
 * 
 * @since 02.12.2015
 *
 */
@Component
public class GrupoDtoAssembler {
	public GrupoDto toDto(Grupo grupo){
		return new GrupoDto(grupo.id().toLong(), grupo.nome());
	}
}
