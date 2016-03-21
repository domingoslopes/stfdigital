package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.suporte.domain.model.TribunalJuizo;

/**
 * Cria objetos JuizoOrigemDto.
 * 
 * @author Anderson.Araujo
 * @since 17.03.2016
 *
 */
@Component
public class TribunalJuizoDtoAssembler {
	public TribunalJuizoDto toDo(TribunalJuizo tribunalJuizo){
		return new TribunalJuizoDto(tribunalJuizo.toLong(), tribunalJuizo.nome());
	}
}
