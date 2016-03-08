package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.suporte.domain.model.TipoModelo;

/**
 * Assembler de TipoModeloDto
 * 
 * @author Tomas.Godoi
 *
 */
@Component
public class TipoModeloDtoAssembler {

	public TipoModeloDto toDto(TipoModelo tipoModelo) {
		Validate.notNull(tipoModelo);
		
		return new TipoModeloDto(tipoModelo.id().toLong(), tipoModelo.descricao());
	}
	
}
