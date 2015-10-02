package br.jus.stf.plataforma.identidades.interfaces.dto;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.identidades.domain.model.Pessoa;


/**
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 23.07.2015
 */
@Component
public class PessoaDtoAssembler {
	
	public PessoaDto toDto(Pessoa pessoa) {
		Validate.notNull(pessoa);
		return new PessoaDto(pessoa.id().toLong(), pessoa.nome());
	}
}
