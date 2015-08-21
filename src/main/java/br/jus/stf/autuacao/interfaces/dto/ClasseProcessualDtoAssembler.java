package br.jus.stf.autuacao.interfaces.dto;

import br.jus.stf.autuacao.domain.entity.ClasseProcessual;

/**
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 21.07.2015
 */
public class ClasseProcessualDtoAssembler {
	public ClasseDto toDto(ClasseProcessual classe) {
		return new ClasseDto(classe.getSigla(), classe.getNome());
	}
}
