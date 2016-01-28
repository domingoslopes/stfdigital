package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.jurisprudencia.controletese.domain.model.Tese;

/**
 * Converte objetos do tipo Tese e objetos do tipo TeseDto.
 * 
 * @author anderson.araujo
 * 
 * @since 28.01.2016
 */
@Component
public class TeseDtoAssembler {
	public TeseDto toDto(Tese tese) {
		Validate.notNull(tese);
		return new TeseDto(tese.id().toLong(), tese.descricao(), tese.numero());
	}
}
