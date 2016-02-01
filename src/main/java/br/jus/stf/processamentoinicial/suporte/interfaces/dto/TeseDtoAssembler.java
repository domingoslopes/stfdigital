package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private AssuntoDtoAssembler assuntoDtoAssembler;
	
	public TeseDto toDto(Tese tese) {
		Validate.notNull(tese);
		List<AssuntoDto> assuntos = tese.assuntos().stream().map(assunto -> assuntoDtoAssembler.toDto(assunto)).collect(Collectors.toList()); 
		
		return new TeseDto(tese.id().toLong(), tese.descricao(), tese.numero(), assuntos);
	}
}
