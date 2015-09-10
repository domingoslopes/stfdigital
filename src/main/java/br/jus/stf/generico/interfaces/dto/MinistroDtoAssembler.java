package br.jus.stf.generico.interfaces.dto;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.generico.domain.model.Ministro;


/**
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 23.07.2015
 */
@Component
public class MinistroDtoAssembler {
	
	public MinistroDto toDto(Ministro ministro ) {
		Validate.notNull(ministro);
		return new MinistroDto(ministro.codigo().toString(), ministro.nome());
	}
}
