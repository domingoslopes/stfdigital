package br.jus.stf.plataforma.acessos.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.acessos.domain.model.Recurso;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class RecursoDtoAssembler {

	public RecursoDto toDto(Recurso recurso) {
		return new RecursoDto(recurso.id().toLong(), recurso.nome(), recurso.tipo().name());
	}
	
}
