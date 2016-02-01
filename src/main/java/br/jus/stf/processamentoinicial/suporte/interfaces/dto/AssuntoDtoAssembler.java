package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.jurisprudencia.controletese.domain.model.Assunto;

/**
 * Converte objetos do tipo Assunto e objetos do tipo AssuntoDto.
 * 
 * @author anderson.araujo
 * 
 * @since 26.01.2016
 */
@Component
public class AssuntoDtoAssembler {
	public AssuntoDto toDto(Assunto assunto) {
		Validate.notNull(assunto);
		return new AssuntoDto(assunto.id().toString(), assunto.descricao());
	}
}
