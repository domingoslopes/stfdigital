package br.jus.stf.processamentoinicial.recursaledistribuicao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.AssuntoAdapter;
import br.jus.stf.processamentoinicial.suporte.interfaces.AssuntoRestResource;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDto;
import br.jus.stf.shared.AssuntoId;

@Component
public class AssuntoRestAdapter implements AssuntoAdapter {

	@Autowired
	private AssuntoRestResource assuntoRestResource;
	
	/**
	 * Consulta um assunto pelo Id
	 * 
	 * @param assuntoId
	 * @return AssuntoDto
	 */
	@Override
	public AssuntoDto consultar(AssuntoId assuntoId) {
		return assuntoRestResource.consultar(assuntoId.toString());
	}
}
