package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.suporte.domain.model.Classe;

/**
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 21.07.2015
 */
@Component
public class ClasseDtoAssembler {
	
	public ClasseDto toDto(Classe classe) {
		Validate.notNull(classe);
		return new ClasseDto(classe.id().toString(), classe.nome());
	}
}
