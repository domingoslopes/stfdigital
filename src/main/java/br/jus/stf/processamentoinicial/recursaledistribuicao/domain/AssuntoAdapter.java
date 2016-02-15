package br.jus.stf.processamentoinicial.recursaledistribuicao.domain;

import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDto;
import br.jus.stf.shared.AssuntoId;

public interface AssuntoAdapter {
	/**
	 * Consulta um assunto pelo Id
	 * 
	 * @param assuntoId
	 * @return AssuntoDto
	 */
	AssuntoDto consultar(AssuntoId id);
}
