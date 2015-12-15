package br.jus.stf.plataforma.acessos.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.acessos.domain.model.Setor;

@Component
public class SetorDtoAssembler {
	public SetorDto toDto(Setor setor) {
		if (setor == null) {
			return null;
		}
		
		return new SetorDto(
			setor.codigo(),
			setor.nome(),
			setor.sigla()
		);
	}
}
